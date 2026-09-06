[CmdletBinding()]
param()

$ErrorActionPreference = 'Continue'

$backendRoot = Split-Path -Parent $PSScriptRoot
$image = 'energycore-platform:cloud-run-local'
$appContainer = 'energycore-platform-cloudrun-test'
$dbContainer = 'energycore-postgres-cloudrun-test'
$network = 'energycore-cloudrun-test'

$dbName = 'energycore'
$dbUser = 'energycore'
$testEmail = 'portfolio.test@energycore.dev'
$frontendOrigin = 'https://university-energycorp.web.app'

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

function Remove-EnergyCoreTestResources {
    $appIds = @(docker ps --all --quiet --filter "name=$appContainer")
    if ($LASTEXITCODE -eq 0 -and $appIds.Count -gt 0) {
        docker rm --force $appContainer | Out-Null
    }

    $databaseIds = @(docker ps --all --quiet --filter "name=$dbContainer")
    if ($LASTEXITCODE -eq 0 -and $databaseIds.Count -gt 0) {
        docker rm --force $dbContainer | Out-Null
    }

    $networkIds = @(docker network ls --quiet --filter "name=$network")
    if ($LASTEXITCODE -eq 0 -and $networkIds.Count -gt 0) {
        docker network rm $network | Out-Null
    }
}

$dbPassword = New-RandomBase64 -Length 32
$jwtSecret = New-RandomBase64 -Length 64

try {
    Set-Location -LiteralPath $backendRoot
    Remove-EnergyCoreTestResources

    docker build --tag $image .
    if ($LASTEXITCODE -ne 0) {
        throw 'La construcción de la imagen falló.'
    }

    docker network create $network | Out-Null
    if ($LASTEXITCODE -ne 0) {
        throw 'No se pudo crear la red temporal.'
    }

    docker run --detach `
        --name $dbContainer `
        --network $network `
        --env "POSTGRES_DB=$dbName" `
        --env "POSTGRES_USER=$dbUser" `
        --env "POSTGRES_PASSWORD=$dbPassword" `
        postgres:17-alpine | Out-Null

    if ($LASTEXITCODE -ne 0) {
        throw 'No se pudo iniciar PostgreSQL temporal.'
    }

    $databaseReady = $false
    $databaseDeadline = (Get-Date).AddMinutes(2)

    do {
        docker exec $dbContainer pg_isready `
            --username=$dbUser `
            --dbname=$dbName 2>$null | Out-Null

        if ($LASTEXITCODE -eq 0) {
            $databaseReady = $true
            break
        }

        Start-Sleep -Seconds 2
    } while ((Get-Date) -lt $databaseDeadline)

    if (-not $databaseReady) {
        throw 'PostgreSQL temporal no quedó disponible.'
    }

    docker run --detach `
        --name $appContainer `
        --network $network `
        --memory 512m `
        --cpus 1 `
        --publish '127.0.0.1:9090:9090' `
        --env 'SPRING_PROFILES_ACTIVE=prod' `
        --env 'PORT=9090' `
        --env "SPRING_DATASOURCE_URL=jdbc:postgresql://${dbContainer}:5432/${dbName}?sslmode=disable" `
        --env "SPRING_DATASOURCE_USERNAME=$dbUser" `
        --env "SPRING_DATASOURCE_PASSWORD=$dbPassword" `
        --env "JWT_SECRET=$jwtSecret" `
        --env "CORS_ALLOWED_ORIGINS=$frontendOrigin,http://localhost:4200" `
        --env 'JAVA_TOOL_OPTIONS=-XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=25.0 -XX:+UseSerialGC' `
        $image | Out-Null

    if ($LASTEXITCODE -ne 0) {
        throw 'No se pudo iniciar el backend.'
    }

    $healthResponse = $null
    $healthDeadline = (Get-Date).AddMinutes(3)

    do {
        try {
            $healthResponse = Invoke-WebRequest `
                -Uri 'http://127.0.0.1:9090/api/v1/health' `
                -UseBasicParsing `
                -TimeoutSec 10 `
                -ErrorAction Stop

            if ($healthResponse.StatusCode -eq 200) {
                break
            }
        }
        catch {
            $healthResponse = $null
            Start-Sleep -Seconds 2
        }
    } while ((Get-Date) -lt $healthDeadline)

    if ($null -eq $healthResponse -or $healthResponse.StatusCode -ne 200) {
        docker logs --tail 100 $appContainer
        throw 'El backend no respondió al health check.'
    }

    $signupBody = @{
        fullName = 'Portfolio Test'
        email = $testEmail
        password = 'Portfolio-Test-2026!'
    } | ConvertTo-Json

    $signupResponse = Invoke-WebRequest `
        -Uri 'http://127.0.0.1:9090/api/v1/auth/sign-up' `
        -Method Post `
        -ContentType 'application/json' `
        -Body $signupBody `
        -UseBasicParsing `
        -ErrorAction Stop

    $loginBody = @{
        email = $testEmail
        password = 'Portfolio-Test-2026!'
    } | ConvertTo-Json

    $loginResponse = Invoke-WebRequest `
        -Uri 'http://127.0.0.1:9090/api/v1/auth/sign-in' `
        -Method Post `
        -ContentType 'application/json' `
        -Body $loginBody `
        -UseBasicParsing `
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
        -Uri 'http://127.0.0.1:9090/api/v1/auth/sign-in' `
        -Method Options `
        -Headers @{
            Origin = $frontendOrigin
            'Access-Control-Request-Method' = 'POST'
            'Access-Control-Request-Headers' = 'content-type'
        } `
        -UseBasicParsing `
        -ErrorAction Stop

    $containerResources = docker inspect `
        --format '{{.HostConfig.Memory}}|{{.HostConfig.NanoCpus}}' `
        $appContainer

    if ($LASTEXITCODE -ne 0) {
        throw 'No se pudo inspeccionar el contenedor.'
    }

    $resourceParts = $containerResources -split '\|', 2
    $startupLine = docker logs $appContainer 2>&1 |
        Select-String -Pattern 'Tomcat started on port 9090' |
        Select-Object -Last 1

    [PSCustomObject]@{
        Image                 = $image
        HealthStatus          = $healthResponse.StatusCode
        SignupStatus          = $signupResponse.StatusCode
        LoginStatus           = $loginResponse.StatusCode
        JwtReturned           = -not [string]::IsNullOrWhiteSpace([string]$tokenValue)
        CorsAllowedOrigin     = [string]$corsResponse.Headers['Access-Control-Allow-Origin']
        InternalPort          = 9090
        Listening             = [string]$startupLine
        MemoryBytes           = $resourceParts[0]
        NanoCpus              = $resourceParts[1]
        TemporaryDatabaseOnly = $true
    } | Format-List
}
finally {
    Remove-EnergyCoreTestResources
    $dbPassword = $null
    $jwtSecret = $null
}
