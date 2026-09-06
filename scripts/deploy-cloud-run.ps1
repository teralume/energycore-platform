[CmdletBinding()]
param(
    [switch]$AllowBillingLink
)

$ErrorActionPreference = 'Continue'

$projectId = 'university-energycorp'
$projectDisplayName = 'University - EnergyCorp'
$folderId = '1062944442437'
$billingAccountId = '01705E-BF0C87-98822A'
$region = 'us-east1'
$repository = 'energycore'
$service = 'energycore-platform'
$serviceAccountName = 'energycore-run'
$serviceAccountEmail = "$serviceAccountName@$projectId.iam.gserviceaccount.com"
$databaseSecretName = 'energycore-database-password'
$jwtSecretName = 'energycore-jwt-secret'
$localImage = 'energycore-platform:cloud-run-local'
$remoteImage = "$region-docker.pkg.dev/$projectId/$repository/energycore-platform:cloud-run"
$frontendOrigin = 'https://university-energycorp.web.app'
$firebaseAppOrigin = 'https://university-energycorp.firebaseapp.com'
$backendRoot = Split-Path -Parent $PSScriptRoot

function Assert-NativeCommand {
    param([Parameter(Mandatory)][string]$Message)

    if ($LASTEXITCODE -ne 0) {
        throw $Message
    }
}

function ConvertTo-PlainText {
    param([Parameter(Mandatory)][Security.SecureString]$SecureValue)

    $pointer = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($SecureValue)

    try {
        return [Runtime.InteropServices.Marshal]::PtrToStringBSTR($pointer)
    }
    finally {
        [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($pointer)
    }
}

function New-RandomBase64 {
    param([Parameter(Mandatory)][int]$Length)

    $bytes = New-Object byte[] $Length
    $generator = [Security.Cryptography.RandomNumberGenerator]::Create()

    try {
        $generator.GetBytes($bytes)
        return [Convert]::ToBase64String($bytes)
    }
    finally {
        $generator.Dispose()
    }
}

function Add-SecretVersionFromMemory {
    param(
        [Parameter(Mandatory)][string]$SecretName,
        [Parameter(Mandatory)][string]$Value
    )

    $temporaryDirectory = [IO.Path]::GetTempPath()
    $temporaryPath = Join-Path $temporaryDirectory (
        "energycore-secret-{0}.tmp" -f [Guid]::NewGuid().ToString('N')
    )

    try {
        $utf8WithoutBom = New-Object Text.UTF8Encoding($false)
        [IO.File]::WriteAllText($temporaryPath, $Value, $utf8WithoutBom)

        gcloud secrets versions add $SecretName `
            --project=$projectId `
            --data-file=$temporaryPath `
            --quiet | Out-Null

        Assert-NativeCommand "No se pudo agregar una versión al secreto $SecretName."
    }
    finally {
        if (Test-Path -LiteralPath $temporaryPath) {
            $length = (Get-Item -LiteralPath $temporaryPath).Length
            if ($length -gt 0) {
                [IO.File]::WriteAllBytes($temporaryPath, (New-Object byte[] $length))
            }
            Remove-Item -LiteralPath $temporaryPath -Force
        }
    }
}

function Ensure-Secret {
    param(
        [Parameter(Mandatory)][string]$SecretName,
        [Parameter(Mandatory)][string]$InitialValue
    )

    $existingSecret = gcloud secrets describe $SecretName `
        --project=$projectId `
        --format='value(name)' 2>$null

    if ($LASTEXITCODE -ne 0 -or [string]::IsNullOrWhiteSpace($existingSecret)) {
        gcloud secrets create $SecretName `
            --project=$projectId `
            --replication-policy=automatic `
            --quiet | Out-Null

        Assert-NativeCommand "No se pudo crear el secreto $SecretName."
        Add-SecretVersionFromMemory -SecretName $SecretName -Value $InitialValue
    }
    else {
        Write-Host "Se reutiliza el secreto existente $SecretName sin crear otra versión."
    }
}

function Get-HttpStatusFromException {
    param([Parameter(Mandatory)]$Exception)

    if ($null -ne $Exception.Response) {
        return [int]$Exception.Response.StatusCode
    }

    return 0
}

if (-not (Test-Path -LiteralPath $backendRoot)) {
    throw "No se encontró el backend: $backendRoot"
}

$localImageId = docker image inspect $localImage --format '{{.Id}}' 2>$null
if ($LASTEXITCODE -ne 0 -or [string]::IsNullOrWhiteSpace($localImageId)) {
    throw "No existe la imagen local validada $localImage. Ejecuta primero validate-cloud-run-local.ps1."
}

$pooledUrlSecure = Read-Host 'Pega la conexión POOLED del proyecto Neon EnergyCore' -AsSecureString
$pooledUrl = ConvertTo-PlainText -SecureValue $pooledUrlSecure
$pooledUri = $null

try {
    $pooledUri = [Uri]$pooledUrl
}
catch {
    throw 'La conexión de Neon no es una URL válida.'
}

if ($pooledUri.Scheme -notin @('postgres', 'postgresql')) {
    throw 'La conexión debe comenzar con postgres:// o postgresql://.'
}

if ($pooledUri.DnsSafeHost -notmatch '-pooler\.') {
    throw 'La conexión no parece ser pooled: el host de Neon debe contener -pooler.'
}

$databaseCredentials = $pooledUri.UserInfo -split ':', 2
if ($databaseCredentials.Count -ne 2) {
    throw 'La conexión Neon no contiene usuario y contraseña válidos.'
}

$databaseHost = $pooledUri.DnsSafeHost
$databasePort = if ($pooledUri.Port -gt 0) { $pooledUri.Port } else { 5432 }
$databaseName = [Uri]::UnescapeDataString($pooledUri.AbsolutePath.TrimStart('/'))
$databaseUser = [Uri]::UnescapeDataString($databaseCredentials[0])
$databasePassword = [Uri]::UnescapeDataString($databaseCredentials[1])

if ([string]::IsNullOrWhiteSpace($databaseName)) {
    throw 'La conexión Neon no contiene el nombre de la base de datos.'
}

$jdbcUrl = "jdbc:postgresql://${databaseHost}:${databasePort}/${databaseName}?sslmode=require"
$jwtSecret = New-RandomBase64 -Length 64

$testEmail = Read-Host 'Correo de prueba (Enter para portfolio.test@energycore.dev)'
if ([string]::IsNullOrWhiteSpace($testEmail)) {
    $testEmail = 'portfolio.test@energycore.dev'
}

$testPasswordSecure = Read-Host 'Contraseña del usuario de prueba (mínimo 8 caracteres)' -AsSecureString
$testPassword = ConvertTo-PlainText -SecureValue $testPasswordSecure

if ($testPassword.Length -lt 8) {
    throw 'La contraseña de prueba debe contener al menos 8 caracteres.'
}

try {
    $activeAccount = (
        gcloud auth list `
            --filter='status:ACTIVE' `
            --format='value(account)'
    ).Trim()

    Assert-NativeCommand 'No se pudo consultar la cuenta activa de gcloud.'

    if ([string]::IsNullOrWhiteSpace($activeAccount)) {
        throw 'gcloud no tiene una cuenta activa.'
    }

    Write-Host "Cuenta activa: $activeAccount"

    $folderDescription = gcloud resource-manager folders describe $folderId `
        --format='value(name,displayName,parent,lifecycleState)'

    Assert-NativeCommand 'No se pudo verificar la carpeta University.'

    if ($folderDescription -notmatch 'University' -or $folderDescription -notmatch 'ACTIVE') {
        throw 'La carpeta indicada no corresponde a University o no está activa.'
    }

    $existingProject = gcloud projects describe $projectId `
        --format='value(projectId)' 2>$null

    if ($LASTEXITCODE -ne 0 -or [string]::IsNullOrWhiteSpace($existingProject)) {
        gcloud projects create $projectId `
            --name=$projectDisplayName `
            --folder=$folderId `
            --quiet

        Assert-NativeCommand "No se pudo crear el proyecto $projectId."
    }
    else {
        $projectParent = (
            gcloud projects describe $projectId `
                --format='value(parent.type,parent.id)'
        ).Trim()

        Assert-NativeCommand 'No se pudo comprobar el padre del proyecto.'

        if ($projectParent -notmatch "folder\s+$folderId") {
            throw "El proyecto $projectId existe, pero no pertenece a la carpeta University esperada."
        }

        Write-Host "Se reutiliza el proyecto existente $projectId."
    }

    $deploymentRoles = @(
        'roles/artifactregistry.admin',
        'roles/iam.serviceAccountAdmin',
        'roles/iam.serviceAccountUser',
        'roles/secretmanager.admin',
        'roles/run.admin'
    )

    foreach ($role in $deploymentRoles) {
        gcloud projects add-iam-policy-binding $projectId `
            --member="user:$activeAccount" `
            --role=$role `
            --quiet | Out-Null

        Assert-NativeCommand "No se pudo asignar el rol requerido $role."
    }

    Write-Host 'Permisos mínimos de despliegue aplicados.'

    $billingEnabled = (
        gcloud billing projects describe $projectId `
            --format='value(billingEnabled)' 2>$null
    ).Trim()

    if ($billingEnabled -ne 'True') {
        if (-not $AllowBillingLink) {
            throw (
                "El proyecto no tiene facturación habilitada. " +
                "Por seguridad y control de gasto, el script no la vincula sin -AllowBillingLink."
            )
        }

        gcloud billing projects link $projectId `
            --billing-account=$billingAccountId `
            --quiet | Out-Null

        Assert-NativeCommand 'No se pudo vincular la cuenta de facturación.'
    }
    else {
        Write-Host 'La facturación ya estaba habilitada; no se modificó.'
    }

    gcloud services enable `
        run.googleapis.com `
        artifactregistry.googleapis.com `
        secretmanager.googleapis.com `
        iam.googleapis.com `
        --project=$projectId `
        --quiet

    Assert-NativeCommand 'No se pudieron habilitar las APIs requeridas.'

    $existingRepository = gcloud artifacts repositories describe $repository `
        --location=$region `
        --project=$projectId `
        --format='value(name)' 2>$null

    if ($LASTEXITCODE -ne 0 -or [string]::IsNullOrWhiteSpace($existingRepository)) {
        gcloud artifacts repositories create $repository `
            --repository-format=docker `
            --location=$region `
            --description='EnergyCore Cloud Run images' `
            --project=$projectId `
            --quiet

        Assert-NativeCommand 'No se pudo crear Artifact Registry.'
    }

    $existingServiceAccount = gcloud iam service-accounts describe $serviceAccountEmail `
        --project=$projectId `
        --format='value(email)' 2>$null

    if ($LASTEXITCODE -ne 0 -or [string]::IsNullOrWhiteSpace($existingServiceAccount)) {
        gcloud iam service-accounts create $serviceAccountName `
            --display-name='EnergyCore Cloud Run' `
            --project=$projectId `
            --quiet

        Assert-NativeCommand 'No se pudo crear la service account.'
    }

    Ensure-Secret `
        -SecretName $databaseSecretName `
        -InitialValue $databasePassword

    Ensure-Secret `
        -SecretName $jwtSecretName `
        -InitialValue $jwtSecret

    foreach ($secretName in @($databaseSecretName, $jwtSecretName)) {
        gcloud secrets add-iam-policy-binding $secretName `
            --project=$projectId `
            --member="serviceAccount:$serviceAccountEmail" `
            --role='roles/secretmanager.secretAccessor' `
            --quiet | Out-Null

        Assert-NativeCommand "No se pudo conceder acceso al secreto $secretName."
    }

    gcloud auth configure-docker "$region-docker.pkg.dev" --quiet
    Assert-NativeCommand 'No se pudo configurar Docker para Artifact Registry.'

    docker tag $localImage $remoteImage
    Assert-NativeCommand 'No se pudo etiquetar la imagen.'

    docker push $remoteImage
    Assert-NativeCommand 'No se pudo subir la imagen a Artifact Registry.'

    $corsOrigins = "$frontendOrigin,$firebaseAppOrigin,http://localhost:4200"
    $resetPasswordUrl = "$frontendOrigin/iam/reset-password"
    $javaOptions = '-XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=25.0 -XX:+UseSerialGC'
    $environmentVariables = (
        '^@^' +
        'SPRING_PROFILES_ACTIVE=prod' +
        "@SPRING_DATASOURCE_URL=$jdbcUrl" +
        "@SPRING_DATASOURCE_USERNAME=$databaseUser" +
        "@CORS_ALLOWED_ORIGINS=$corsOrigins" +
        "@ENERGYCORE_WEBAPP_RESET_PASSWORD_URL=$resetPasswordUrl" +
        "@JAVA_TOOL_OPTIONS=$javaOptions"
    )
    $secretMappings = (
        "SPRING_DATASOURCE_PASSWORD=${databaseSecretName}:latest," +
        "JWT_SECRET=${jwtSecretName}:latest"
    )

    gcloud run deploy $service `
        --project=$projectId `
        --region=$region `
        --image=$remoteImage `
        --service-account=$serviceAccountEmail `
        --port=8080 `
        --cpu=1 `
        --memory=512Mi `
        --concurrency=10 `
        --min=0 `
        --max=1 `
        --cpu-throttling `
        --no-cpu-boost `
        --execution-environment=gen2 `
        --ingress=all `
        --allow-unauthenticated `
        --timeout=300 `
        --startup-probe='httpGet.path=/api/v1/health,timeoutSeconds=5,periodSeconds=5,failureThreshold=36' `
        --set-env-vars=$environmentVariables `
        --set-secrets=$secretMappings `
        --labels='application=energycore,environment=portfolio,owner=university' `
        --no-deploy-health-check `
        --quiet

    Assert-NativeCommand 'El despliegue de Cloud Run falló.'

    $serviceUrl = (
        gcloud run services describe $service `
            --project=$projectId `
            --region=$region `
            --format='value(status.url)'
    ).Trim()

    Assert-NativeCommand 'No se pudo obtener la URL de Cloud Run.'

    if ([string]::IsNullOrWhiteSpace($serviceUrl)) {
        throw 'Cloud Run no devolvió una URL pública.'
    }

    $coldStartTimer = [Diagnostics.Stopwatch]::StartNew()
    $healthResponse = Invoke-WebRequest `
        -Uri "$serviceUrl/api/v1/health" `
        -UseBasicParsing `
        -TimeoutSec 300 `
        -ErrorAction Stop
    $coldStartTimer.Stop()

    $signupBody = @{
        fullName = 'Portfolio Test'
        email = $testEmail
        password = $testPassword
    } | ConvertTo-Json

    $signupStatus = 0

    try {
        $signupResponse = Invoke-WebRequest `
            -Uri "$serviceUrl/api/v1/auth/sign-up" `
            -Method Post `
            -ContentType 'application/json' `
            -Body $signupBody `
            -UseBasicParsing `
            -TimeoutSec 120 `
            -ErrorAction Stop

        $signupStatus = $signupResponse.StatusCode
    }
    catch {
        $signupStatus = Get-HttpStatusFromException -Exception $_.Exception

        if ($signupStatus -notin @(400, 409)) {
            throw
        }
    }

    $loginBody = @{
        email = $testEmail
        password = $testPassword
    } | ConvertTo-Json

    $loginResponse = Invoke-WebRequest `
        -Uri "$serviceUrl/api/v1/auth/sign-in" `
        -Method Post `
        -ContentType 'application/json' `
        -Body $loginBody `
        -UseBasicParsing `
        -TimeoutSec 120 `
        -ErrorAction Stop

    $loginResult = $loginResponse.Content | ConvertFrom-Json
    $tokenValue = @(
        $loginResult.accessToken
        $loginResult.token
        $loginResult.jwt
    ) |
        Where-Object {
            -not [string]::IsNullOrWhiteSpace([string]$_)
        } |
        Select-Object -First 1

    $corsResponse = Invoke-WebRequest `
        -Uri "$serviceUrl/api/v1/auth/sign-in" `
        -Method Options `
        -Headers @{
            Origin = $frontendOrigin
            'Access-Control-Request-Method' = 'POST'
            'Access-Control-Request-Headers' = 'content-type'
        } `
        -UseBasicParsing `
        -TimeoutSec 120 `
        -ErrorAction Stop

    [PSCustomObject]@{
        ProjectId              = $projectId
        ParentFolder           = "folders/$folderId"
        Region                 = $region
        Repository             = $repository
        Image                  = $remoteImage
        ServiceAccount         = $serviceAccountEmail
        ServiceUrl             = $serviceUrl
        HealthStatus           = $healthResponse.StatusCode
        FirstRequestSeconds    = [Math]::Round($coldStartTimer.Elapsed.TotalSeconds, 2)
        SignupStatus           = $signupStatus
        LoginStatus            = $loginResponse.StatusCode
        JwtReturned            = -not [string]::IsNullOrWhiteSpace([string]$tokenValue)
        CorsAllowedOrigin      = [string]$corsResponse.Headers['Access-Control-Allow-Origin']
        RequestedMinInstances  = 0
        RequestedMaxInstances  = 1
        RequestedCpu           = 1
        RequestedMemory        = '512Mi'
        RequestedCpuThrottling = $true
        RequestedCpuBoost      = $false
        TestUserEmail          = $testEmail
    } | Format-List
}
finally {
    $pooledUrl = $null
    $databaseCredentials = $null
    $databasePassword = $null
    $jwtSecret = $null
    $testPassword = $null
    $pooledUrlSecure.Dispose()
    $testPasswordSecure.Dispose()
}
