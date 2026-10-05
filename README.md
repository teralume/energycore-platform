# EnergyCore Platform

RESTful API de EnergyCore implementada con Spring Boot y organizada por
bounded contexts.

## Pruebas y CI/CD

Cada `push` y Pull Request ejecuta los tests unitarios, de integración y de
aceptación BDD con H2, construye el JAR, verifica el Dockerfile y publica los
resultados como artefactos de GitHub Actions.

Los commits integrados en `main` despliegan una nueva revisión en Cloud Run
cuando `GCP_DEPLOY_ENABLED` vale `true` y están configuradas las variables
`GCP_WORKLOAD_IDENTITY_PROVIDER` y `GCP_SERVICE_ACCOUNT`.

```powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
& .\mvnw.cmd --batch-mode verify
```
