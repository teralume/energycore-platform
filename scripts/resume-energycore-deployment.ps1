[CmdletBinding()]
param([switch]$SkipBackendDeployment)

# Run in an interactive PowerShell. Never run with Start-Transcript or tracing.
$ErrorActionPreference = 'Stop'
$project = 'university-energycorp'
$region = 'us-east1'
$service = 'energycore-platform'
$account = "energycore-run@$project.iam.gserviceaccount.com"
$digest = 'sha256:ef8456115c0113a2bb747cdee1cec14a0654e2c9b9d9a42ff82e6541d83e091a'
$image = "us-east1-docker.pkg.dev/$project/energycore/energycore-platform@$digest"
$root = Split-Path (Split-Path $PSScriptRoot -Parent) -Parent
$web = Join-Path $root 'energycore-webapp'
$origin = 'https://university-energycorp.web.app'
$phase = 'preflight'
$evidencePath = Join-Path $PSScriptRoot 'deployment-result.local.json'
$evidence = [ordered]@{ StartedUtc = [DateTime]::UtcNow.ToString('o'); Image = $image; Status = 'IN_PROGRESS'; Android = 'NOT_VERIFIED'; BrowserJourney = 'NOT_VERIFIED'; PersistenceAcrossRevision = 'NOT_VERIFIED' }

function Assert-Exit([string]$Step) {
    if ($LASTEXITCODE -ne 0) { throw "$Step failed (exit $LASTEXITCODE)." }
}
function Cloud([string[]]$Arguments) {
    $result = & gcloud @Arguments --project=$project --quiet
    Assert-Exit ($Arguments[0..([Math]::Min(2, $Arguments.Count - 1))] -join ' ')
    return $result
}
function Random-Secret([int]$ByteCount = 64) {
    $bytes = New-Object byte[] $ByteCount
    $rng = [Security.Cryptography.RandomNumberGenerator]::Create()
    try { $rng.GetBytes($bytes); [Convert]::ToBase64String($bytes) } finally { $rng.Dispose() }
}
function Read-Hidden([string]$Prompt) {
    $secure = Read-Host $Prompt -AsSecureString
    $ptr = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($secure)
    try { [Runtime.InteropServices.Marshal]::PtrToStringBSTR($ptr) }
    finally { [Runtime.InteropServices.Marshal]::ZeroFreeBSTR($ptr); $secure.Dispose() }
}
function Add-Secret([string]$Name, [string]$Value) {
    # Secret bytes travel via stdin, never via command arguments or a temporary file.
    $cli = (Get-Command gcloud.cmd -ErrorAction Stop).Source
    $info = New-Object Diagnostics.ProcessStartInfo
    $info.FileName = $env:ComSpec
    $info.Arguments = '/d /s /c ""' + $cli + '" secrets versions add ' + $Name + ' --project=' + $project + ' --data-file=- --quiet"'
    $info.UseShellExecute = $false
    $info.CreateNoWindow = $true
    $info.RedirectStandardInput = $true
    $process = New-Object Diagnostics.Process
    $process.StartInfo = $info
    try {
        [void]$process.Start()
        $process.StandardInput.Write($Value)
        $process.StandardInput.Close()
        $process.WaitForExit()
        if ($process.ExitCode -ne 0) { throw "Secret upload failed: $Name (exit $($process.ExitCode))." }
    } finally { $process.Dispose() }
}
function Secret-Version([string]$Name) {
    $names = @(Cloud @('secrets','list','--format=value(name)'))
    if (-not ($names | Where-Object { ($_ -split '/')[-1] -eq $Name })) {
        Cloud @('secrets','create',$Name,'--replication-policy=automatic') | Out-Null
    }
    $versions = @(Cloud @('secrets','versions','list',$Name,'--filter=state:ENABLED','--sort-by=~createTime','--limit=1','--format=value(name)'))
    if ($versions.Count -eq 0) { return $null }
    return ($versions[0] -split '/')[-1]
}
function Request([string]$Path, [string]$Method = 'GET', $Body = $null, $Headers = @{}) {
    $options = @{ Uri = "$script:api$Path"; Method = $Method; Headers = $Headers; UseBasicParsing = $true; TimeoutSec = 300 }
    if ($null -ne $Body) { $options.Body = $Body | ConvertTo-Json -Compress; $options.ContentType = 'application/json' }
    try { Invoke-WebRequest @options }
    catch {
        $status = 'unavailable'
        if ($null -ne $_.Exception.Response) { $status = [string][int]$_.Exception.Response.StatusCode }
        $evidence.LastHttpFailure = @{ Method=$Method; Path=$Path; Status=$status }
        throw "Public API check failed: $Method $Path (HTTP $status). Response omitted to protect credentials."
    }
}

try {
    foreach ($tool in @('gcloud.cmd','npm.cmd','npx.cmd')) { Get-Command $tool -ErrorAction Stop | Out-Null }
    $active = & gcloud auth list --filter=status:ACTIVE --format='value(account)'
    Assert-Exit 'gcloud auth'
    if (-not $active) { throw 'No active gcloud session. Run gcloud auth login first.' }
    Write-Host "[1/5] Checking existing project, billing, image and Firebase. Account: $active"
    $p = (Cloud @('projects','describe',$project,'--format=json') | Out-String) | ConvertFrom-Json
    if ([string]$p.projectNumber -ne '624519427815') { throw 'Unexpected project number.' }
    $billing = (Cloud @('billing','projects','describe',$project,'--format=json') | Out-String) | ConvertFrom-Json
    if (-not $billing.billingEnabled -or $billing.billingAccountName -ne 'billingAccounts/01705E-BF0C87-98822A') { throw 'Expected billing account is not enabled.' }
    Cloud @('iam','service-accounts','describe',$account,'--format=value(email)') | Out-Null
    Cloud @('run','services','list',"--region=$region",'--format=table(metadata.name,status.url)') | Out-Host
    $actualDigest = Cloud @('artifacts','docker','images','describe',$image,'--format=value(image_summary.digest)')
    if ($actualDigest -ne $digest) { throw 'Registry digest does not match validated image.' }
    $sitesText = & npx.cmd --yes firebase-tools@15.15.0 hosting:sites:list --project $project --json --non-interactive
    Assert-Exit 'Firebase site inventory'
    if (($sitesText | Out-String) -notmatch 'sites/university-energycorp"') { throw 'Expected Angular Hosting site is missing.' }

    if (-not $SkipBackendDeployment) {
    $phase = 'secrets'
    Write-Host '[2/5] Preparing dedicated secrets. Existing enabled versions are reused.'
    $dbVersion = Secret-Version 'energycore-database-password'
    $jwtVersion = Secret-Version 'energycore-jwt-secret'
    $pooled = Read-Hidden 'Paste the POOLED connection URL from the existing EnergyCore Neon project (hidden)'
    try { $uri = [Uri]$pooled } catch { throw 'Invalid Neon URL (value omitted).' }
    if ($uri.Scheme -notin @('postgres','postgresql') -or $uri.DnsSafeHost -notmatch '^ep-super-frost-auww7roc-pooler\.[a-z0-9.-]+\.neon\.tech$' -or $uri.AbsolutePath -ne '/neondb') { throw 'Connection is not the dedicated EnergyCore pooled endpoint.' }
    $credentials = $uri.UserInfo -split ':',2
    if ($credentials.Count -ne 2 -or [Uri]::UnescapeDataString($credentials[0]) -ne 'neondb_owner') { throw 'Unexpected Neon role or missing password.' }
    if (-not $dbVersion) {
        Add-Secret 'energycore-database-password' ([Uri]::UnescapeDataString($credentials[1]))
        $dbVersion = Secret-Version 'energycore-database-password'
    }
    if (-not $jwtVersion) {
        Add-Secret 'energycore-jwt-secret' (Random-Secret)
        $jwtVersion = Secret-Version 'energycore-jwt-secret'
    }
    $jdbc = "jdbc:postgresql://$($uri.DnsSafeHost):5432/neondb?sslmode=require"
    $pooled = $null; $credentials = $null; $uri = $null
    foreach ($secret in @('energycore-database-password','energycore-jwt-secret')) {
        Cloud @('secrets','add-iam-policy-binding',$secret,"--member=serviceAccount:$account",'--role=roles/secretmanager.secretAccessor','--format=none') | Out-Null
    }

    $phase = 'cloud-run'
    Write-Host '[3/5] Deploying validated digest, min 0 / max 1, 512 MiB, 1 CPU.'
    $vars = '^@^SPRING_PROFILES_ACTIVE=prod@SPRING_DATASOURCE_URL=' + $jdbc + '@SPRING_DATASOURCE_USERNAME=neondb_owner@CORS_ALLOWED_ORIGINS=' + $origin + ',https://university-energycorp.firebaseapp.com,http://localhost:4200@ENERGYCORE_WEBAPP_RESET_PASSWORD_URL=' + $origin + '/iam/reset-password@JAVA_TOOL_OPTIONS=-XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=25.0 -XX:+UseSerialGC'
    Cloud @('run','deploy',$service,"--region=$region","--image=$image","--service-account=$account",'--port=8080','--cpu=1','--memory=512Mi','--concurrency=10','--min=0','--max=1','--cpu-throttling','--no-cpu-boost','--execution-environment=gen2','--ingress=all','--allow-unauthenticated','--timeout=300','--startup-probe=httpGet.path=/api/v1/health,timeoutSeconds=5,periodSeconds=5,failureThreshold=36',"--set-env-vars=$vars","--set-secrets=SPRING_DATASOURCE_PASSWORD=energycore-database-password:$dbVersion,JWT_SECRET=energycore-jwt-secret:$jwtVersion") | Out-Null
    } else {
        Write-Host '[2/5] Reusing deployed backend. No secret, IAM or revision changes.'
    }
    $phase = 'api-acceptance'
    $config = (Cloud @('run','services','describe',$service,"--region=$region",'--format=json') | Out-String) | ConvertFrom-Json
    if ($config.spec.template.spec.containers[0].image -ne $image) { throw 'Existing service does not use the expected immutable image.' }
    $api = [string]$config.status.url
    if ($api -notmatch '^https://[a-z0-9.-]+\.run\.app$') { throw 'Missing or unexpected Cloud Run URL.' }
    $evidence.ApiUrl = $api
    $evidence.Revision = $config.status.latestReadyRevisionName
    $annotations = $config.spec.template.metadata.annotations
    $container = $config.spec.template.spec.containers[0]
    $max = $config.metadata.annotations.'run.googleapis.com/maxScale'
    if (-not $max) { $max = $annotations.'autoscaling.knative.dev/maxScale' }
    $min = $config.metadata.annotations.'run.googleapis.com/minScale'
    if (-not $min) { $min = $annotations.'autoscaling.knative.dev/minScale' }
    if (-not $min) { $min = '0' }
    if ($max -ne '1' -or $min -ne '0' -or $container.resources.limits.cpu -notin @('1','1000m') -or $container.resources.limits.memory -ne '512Mi' -or $config.spec.template.spec.containerConcurrency -ne 10 -or $annotations.'run.googleapis.com/cpu-throttling' -ne 'true') { throw 'Deployed Cloud Run limits differ from requested guardrails.' }
    $evidence.Capacity = @{ Min=$min; Max=$max; Cpu=$container.resources.limits.cpu; Memory=$container.resources.limits.memory; Concurrency=10; CpuThrottling=$true }
    $evidence.Health = (Request '/api/v1/health').StatusCode
    $spec = (Request '/v3/api-docs').Content | ConvertFrom-Json
    if (-not $spec.openapi -or -not $spec.paths) { throw 'Invalid OpenAPI document.' }
    $evidence.OpenApi = 'VERIFIED'
    $evidence.Swagger = (Request '/swagger-ui.html').StatusCode
    # 32 random bytes produce 44 ASCII bytes, below BCrypt's 72-byte limit.
    # JWT secret generation remains 64 random bytes.
    $testPassword = Random-Secret -ByteCount 32
    $email = 'deploy.' + [Guid]::NewGuid().ToString('N') + '@energycore.dev'
    $evidence.Signup = (Request '/api/v1/auth/sign-up' 'POST' @{ fullName='Deployment Verification'; email=$email; password=$testPassword }).StatusCode
    $login = (Request '/api/v1/auth/sign-in' 'POST' @{ email=$email; password=$testPassword }).Content | ConvertFrom-Json
    $token = @($login.accessToken,$login.token,$login.jwt) | Where-Object { $_ } | Select-Object -First 1
    if (-not $token) { throw 'Login did not return a JWT.' }
    $evidence.LoginJwt = 'VERIFIED'
    $evidence.PersistedUserRead = (Request '/api/v1/users/me' 'GET' $null @{Authorization="Bearer $token"}).StatusCode
    $cors = Request '/api/v1/auth/sign-in' 'OPTIONS' $null @{ Origin=$origin; 'Access-Control-Request-Method'='POST'; 'Access-Control-Request-Headers'='content-type' }
    if ($cors.Headers['Access-Control-Allow-Origin'] -ne $origin) { throw 'CORS origin mismatch.' }
    $evidence.Cors = 'VERIFIED'

    $phase = 'firebase'
    Write-Host '[4/5] Checking and publishing Angular against the public API.'
    Push-Location $web
    try {
        & npm.cmd test -- --watch=false
        Assert-Exit 'Angular tests'
        & npm.cmd run build
        Assert-Exit 'Angular build'
        $dist = Join-Path $web 'dist/energycore-webapp/browser'
        $files = @(Get-ChildItem $dist -Filter '*.js' -Recurse -File)
        $replaced = 0
        foreach ($file in $files) {
            $bundle = [IO.File]::ReadAllText($file.FullName)
            if ($bundle.Contains('__ENERGYCORE_API_BASE_URL__')) {
                [IO.File]::WriteAllText($file.FullName,$bundle.Replace('__ENERGYCORE_API_BASE_URL__',"$api/api/v1"),(New-Object Text.UTF8Encoding($false)))
                $replaced++
            }
        }
        if ($replaced -eq 0) { throw 'Production API marker not found.' }
        foreach ($file in $files) {
            if ([IO.File]::ReadAllText($file.FullName) -match '__ENERGYCORE_API_BASE_URL__|energycore-platform\.onrender\.com') { throw 'Stale API reference remains in bundle.' }
        }
        & npx.cmd --yes firebase-tools@15.15.0 deploy --only hosting --project $project --non-interactive
        Assert-Exit 'Firebase deploy'
    } finally { Pop-Location }
    $evidence.WebLogin = (Invoke-WebRequest "$origin/iam/login" -UseBasicParsing -TimeoutSec 120).StatusCode
    $landing = Invoke-WebRequest 'https://university-energycorp-landing.web.app' -UseBasicParsing -TimeoutSec 120
    if ($landing.Content -notmatch 'EnergyCore') { throw 'Landing identity check failed.' }
    $evidence.Landing = $landing.StatusCode
    $evidence.WebUrl = $origin
    $evidence.Status = 'API_WEB_DEPLOYED_ACCEPTANCE_PARTIAL'
    Write-Host "[5/5] API and Angular published. API: $api ; Web: $origin"
    Write-Host 'Android, browser workflow and persistence across a new revision still require verification.'
} catch {
    $evidence.Status = 'BLOCKED'
    $evidence.FailedPhase = $phase
    Write-Host "Stopped in phase: $phase. No later stages ran."
    throw
} finally {
    $pooled=$null; $credentials=$null; $uri=$null; $testPassword=$null; $token=$null; $login=$null
    $evidence.FinishedUtc = [DateTime]::UtcNow.ToString('o')
    $evidence | ConvertTo-Json -Depth 20 | Set-Content -LiteralPath $evidencePath -Encoding UTF8
    Write-Host "Evidence (no credentials): $evidencePath"
}
