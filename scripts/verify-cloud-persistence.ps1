[CmdletBinding()]
param([string]$PreparedRevisionName)
$ErrorActionPreference = 'Stop'
$project = 'university-energycorp'
$service = 'energycore-platform'
$region = 'us-east1'
$expectedDigest = 'sha256:ef8456115c0113a2bb747cdee1cec14a0654e2c9b9d9a42ff82e6541d83e091a'
$evidencePath = Join-Path $PSScriptRoot 'persistence-result.local.json'
$result = [ordered]@{ Status='IN_PROGRESS'; StartedUtc=[DateTime]::UtcNow.ToString('o') }
function Read-Service {
    $json = & gcloud run services describe $service --project=$project --region=$region --format=json
    if ($LASTEXITCODE -ne 0) { throw 'Cannot read Cloud Run service.' }
    ($json | Out-String) | ConvertFrom-Json
}
function Api([string]$Path,[string]$Method='GET',$Body=$null,$Token=$null) {
    $options=@{ Uri="$script:api$Path"; Method=$Method; TimeoutSec=180; ErrorAction='Stop' }
    if ($Body) { $options.Body=$Body|ConvertTo-Json -Compress; $options.ContentType='application/json' }
    if ($Token) { $options.Headers=@{Authorization="Bearer $Token"} }
    try { Invoke-RestMethod @options }
    catch {
        $status=0
        if ($_.Exception.Response) { $status=[int]$_.Exception.Response.StatusCode }
        throw "API persistence check failed: $Method $Path HTTP $status (body suppressed)."
    }
}
try {
    $before=Read-Service
    $api=[string]$before.status.url
    $image=[string]$before.spec.template.spec.containers[0].image
    if (-not $image.EndsWith("@$expectedDigest")) { throw 'Unexpected deployed image.' }
    $result.Image=$image
    $result.ApiUrl=$api
    $result.PreviousRevision=$before.status.latestReadyRevisionName
    $dbEnv=$before.spec.template.spec.containers[0].env | Where-Object name -eq 'SPRING_DATASOURCE_URL'
    if ($dbEnv.value -notmatch '^jdbc:postgresql://ep-super-frost-auww7roc-pooler\.[a-z0-9.-]+\.neon\.tech:5432/neondb\?sslmode=require$') { throw 'Service is not using dedicated EnergyCore Neon endpoint.' }
    $result.DatabaseTarget='Dedicated EnergyCore Neon pooled endpoint / neondb'
    $random=New-Object byte[] 32
    $generator=[Security.Cryptography.RandomNumberGenerator]::Create()
    try { $generator.GetBytes($random) } finally { $generator.Dispose() }
    $password=[Convert]::ToBase64String($random)
    $marker='Persistence '+[Guid]::NewGuid().ToString('N')
    $email='persist.'+[Guid]::NewGuid().ToString('N')+'@energycore.dev'
    $auth=Api '/api/v1/auth/sign-up' 'POST' @{fullName=$marker;email=$email;password=$password}
    $token=@($auth.token,$auth.accessToken,$auth.jwt)|Where-Object {$_}|Select-Object -First 1
    if (-not $token) { throw 'Registration did not return a JWT.' }
    $userBefore=Api '/api/v1/users/me' 'GET' $null $token
    if ($userBefore.fullName -ne $marker) { throw 'Initial stored marker mismatch.' }
    $result.UserId=$userBefore.id
    Write-Host 'Stored unique test record. Verifying a revision with the same image and unchanged capacity/secrets.'
    $suffix='persist-'+[DateTime]::UtcNow.ToString('MMddHHmmss')
    $newRevision="$service-$suffix"
    if ($PreparedRevisionName) {
        if ($PreparedRevisionName -notmatch '^energycore-platform-persist-[0-9]+$') { throw 'Unexpected prepared revision name.' }
        $newRevision=$PreparedRevisionName
    } else {
    & gcloud run services update $service --project=$project --region=$region --revision-suffix=$suffix --update-env-vars="ENERGYCORE_PERSISTENCE_CHECK=$suffix" --no-traffic --quiet
    if ($LASTEXITCODE -ne 0) { throw 'New revision preparation failed; existing traffic was not intentionally changed.' }
    }
    $result.PreparedRevision=$newRevision
    $prepared=Read-Service
    # A zero-traffic revision can be Ready/Retired while latestReadyRevisionName still names the serving revision.
    $revisionJson=& gcloud run revisions describe $newRevision --project=$project --region=$region --format=json
    if ($LASTEXITCODE -ne 0) { throw 'Cannot read prepared revision.' }
    $revision=($revisionJson|Out-String)|ConvertFrom-Json
    if (-not ($revision.status.conditions|Where-Object {$_.type -eq 'Ready' -and $_.status -eq 'True'})) { throw 'Prepared revision is not ready.' }
    if ($prepared.status.latestCreatedRevisionName -ne $newRevision) { throw 'Prepared revision differs from service template; inspect before switching traffic.' }
    # Verify relevant configuration was preserved before moving traffic.
    $oldContainer=$before.spec.template.spec.containers[0]
    $newContainer=$prepared.spec.template.spec.containers[0]
    if ($newContainer.image -ne $oldContainer.image -or
        ($oldContainer.resources|ConvertTo-Json -Depth 10 -Compress) -ne ($newContainer.resources|ConvertTo-Json -Depth 10 -Compress) -or
        $before.spec.template.spec.serviceAccountName -ne $prepared.spec.template.spec.serviceAccountName -or
        $before.spec.template.spec.containerConcurrency -ne $prepared.spec.template.spec.containerConcurrency) { throw 'New revision configuration changed unexpectedly; traffic not switched.' }
    $oldEnv=@($oldContainer.env|Where-Object name -ne 'ENERGYCORE_PERSISTENCE_CHECK'|Sort-Object name)|ConvertTo-Json -Depth 10 -Compress
    $newEnv=@($newContainer.env|Where-Object name -ne 'ENERGYCORE_PERSISTENCE_CHECK'|Sort-Object name)|ConvertTo-Json -Depth 10 -Compress
    if ($oldEnv -ne $newEnv) { throw 'Database or secret configuration changed unexpectedly; traffic not switched.' }
    & gcloud run services update-traffic $service --project=$project --region=$region --to-revisions="${newRevision}=100" --quiet
    if ($LASTEXITCODE -ne 0) { throw 'Traffic update did not complete.' }
    $result.NewRevision=$newRevision
    $after=Read-Service
    $serving=@($after.status.traffic|Where-Object {$_.revisionName -eq $newRevision -and $_.percent -eq 100})
    if ($serving.Count -ne 1) { throw 'New revision not confirmed at 100 percent traffic.' }
    Api '/api/v1/health' | Out-Null
    $login=Api '/api/v1/auth/sign-in' 'POST' @{email=$email;password=$password}
    $token=@($login.token,$login.accessToken,$login.jwt)|Where-Object {$_}|Select-Object -First 1
    if (-not $token) { throw 'Login after revision change did not return JWT.' }
    $userAfter=Api '/api/v1/users/me' 'GET' $null $token
    if ($userAfter.id -ne $userBefore.id -or $userAfter.fullName -ne $marker -or $userAfter.email -ne $email) { throw 'Persisted user differs across revisions.' }
    $result.Status='VERIFIED'
    $result.SameUserAcrossRevisions=$true
    $result.LoginAfterRevisionChange=$true
    $result.FinishedUtc=[DateTime]::UtcNow.ToString('o')
    Write-Host "Persistence VERIFIED: $($result.PreviousRevision) -> $newRevision; same user ID and stored marker; login succeeded."
} catch {
    $result.Status='BLOCKED'
    Write-Host "Persistence not verified. Previous revision: $($result.PreviousRevision). Inspect evidence before any retry."
    throw
} finally {
    $password=$null; $auth=$null; $token=$null; $login=$null; $random=$null
    $result|ConvertTo-Json -Depth 10|Set-Content -LiteralPath $evidencePath -Encoding UTF8
    Write-Host "Evidence: $evidencePath"
}
