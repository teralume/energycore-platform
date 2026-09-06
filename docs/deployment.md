# EnergyCore deployment runbook

This runbook deploys the Spring Boot API to Cloud Run and connects it to the pooled PostgreSQL endpoint supplied by Neon. It never stores the Neon connection string or test password in the repository.

## Targets

- GCP project: `university-energycorp`
- Region: `us-east1`
- Cloud Run service: `energycore-platform`
- Artifact Registry repository: `energycore`
- Runtime service account: `energycore-run`
- Capacity guardrails: min 0, max 1, 1 CPU, 512 MiB, concurrency 10, CPU throttling enabled
- Web application origin: `https://university-energycorp.web.app`
- Landing Page origin: `https://university-energycorp-landing.web.app`

## Required local state

1. Docker Desktop must report both Client and Server.
2. `gcloud auth list` must show the intended account as active.
3. The pooled Neon connection string must use `postgresql://` or `postgres://`, include credentials, and have a host containing `-pooler`.
4. Never paste the Neon connection string into chat, Git, screenshots or command history. The deploy script requests it as a secure prompt.

## Release sequence

Run from a normal PowerShell session:

```powershell
Set-Location "C:\JeanLoa\Universidad\Diseño de Experimentos de Ingeniería de Software\energycore-platform"

docker version
& .\scripts\validate-cloud-run-local.ps1
& .\scripts\deploy-cloud-run.ps1
```

The last command refuses to link billing automatically. If the project has no linked billing account, review the account and cost exposure first, then run the deployment explicitly with:

```powershell
& .\scripts\deploy-cloud-run.ps1 -AllowBillingLink
```

After the API succeeds, deploy the Angular application and Landing Page from their own repositories:

```powershell
Set-Location "C:\JeanLoa\Universidad\Diseño de Experimentos de Ingeniería de Software\energycore-webapp"
& .\scripts\deploy-firebase-hosting.ps1

Set-Location "C:\JeanLoa\Universidad\Diseño de Experimentos de Ingeniería de Software\energycore-website"
& .\scripts\deploy-firebase-hosting.ps1
```

The Angular deployment resolves the active Cloud Run URL at runtime and replaces the production API marker inside the generated bundle. No Cloud Run hostname is hardcoded in source.

## Acceptance evidence

Do not declare the release complete until all of the following are recorded:

- `/api/v1/health` returns HTTP 200 from the public Cloud Run URL.
- `/v3/api-docs` returns a valid OpenAPI document and `/swagger-ui.html` loads.
- Sign-up and sign-in work and return a JWT.
- The Firebase origin is accepted by CORS.
- A write followed by a read survives a new Cloud Run revision, demonstrating Neon persistence.
- Cloud Run reports min 0 and max 1.
- The Web Application login route and Landing Page return HTTP 200.
- GitHub `release/av1` branches and Insights screenshots are published before merging to `main`.

