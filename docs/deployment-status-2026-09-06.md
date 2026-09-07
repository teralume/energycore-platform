# EnergyCore deployment acceptance — 2026-09-06 (Lima)

**Web, API and Android direct-install release acceptance: VERIFIED.** Final checks completed on 2026-09-07 UTC (2026-09-06 in Lima). This closes the requested deployment scope; it is not a Google Play/App Store publication or a claim that every historical test suite passed.

## Verified live

- Cloud Run revision serving 100% traffic: `energycore-platform-persist-0907012918`.
- API: https://energycore-platform-ujdgb2zcpq-ue.a.run.app
- Angular: https://university-energycorp.web.app
- Landing: https://university-energycorp-landing.web.app
- Image: `us-east1-docker.pkg.dev/university-energycorp/energycore/energycore-platform@sha256:ef8456115c0113a2bb747cdee1cec14a0654e2c9b9d9a42ff82e6541d83e091a`.
- Health, Swagger, sign-up and authenticated user read: HTTP 200. OpenAPI valid; sign-in returns JWT; Firebase origin passes CORS.
- Cloud Run: min 0, max 1, 1 CPU, 512 MiB, concurrency 10, CPU throttling enabled.
- Angular: 2 tests passed, production build passed with existing size warnings; Firebase published 76 files. Published bundle contains the real API URL and no unresolved API marker.
- Browser: followed the landing link to Angular, registered a synthetic account, loaded its authenticated account and plan catalog from the API, signed out and signed in again. No plan purchase was performed. Registration currently allows only Gmail, Outlook and Hotmail domains; an `energycore.dev` address is rejected by the existing frontend rule before HTTP transmission.

## Deployment-script fix

The first release succeeded but its acceptance test failed: the script generated an 88-byte Base64 password, exceeding BCrypt's 72-byte limit. Public checks reproduced HTTP 400 mentioning the limit; a 44-byte password returned sign-up 200 and a login JWT.

`resume-energycore-deployment.ps1` now generates the test password from 32 random bytes. JWT secret generation remains 64 random bytes. HTTP failures record their status without response bodies or credentials.

The `-SkipBackendDeployment` switch resumes acceptance and Angular publication without changing secrets, IAM or the Cloud Run revision. That continuation completed on the original revision. A later controlled revision change was performed specifically for persistence acceptance. Do not rerun deployment scripts merely to view the published application.

## Android release acceptance

- Built with `flutter build apk --release --dart-define=API_BASE_URL=https://energycore-platform-ujdgb2zcpq-ue.a.run.app/api/v1`.
- Delivery APK: `energycore-mobile/flutter/build/delivery/EnergyCore-1.0.0-release.apk` (58,998,701 bytes). `SHA256SUMS.txt` is beside it.
- SHA-256: `2663C6B88432F7E6B027A68B906FAF72227101AD1032936CC6B17E88D79AEA00`.
- `apksigner verify` passed. APK metadata and installed package flags confirm it is **not debuggable**. Android API 36, version 1.0.0, arm64-v8a/armeabi-v7a/x86_64.
- Existing Android development signing certificate retained to update the installed app without uninstalling or erasing its data. This is an optimized release APK for direct installation, not a store-signing setup. Certificate SHA-256: `5640606fbcd8fef673a84c1c2d9f1f0e1726e2086bd2c3d7d1b614f23de37fb5`.
- Installation returned `Success`; installed `base.apk` hash matches the delivery APK; cold launch returned `Status: ok`.
- Visual verification on Pixel_7: login with a synthetic account opened the plan catalog. Cloud Run logged Dart requests returning HTTP 200 for sign-in, plans, preferences, subscription, payments and invoices on the new revision at 01:36 UTC.
- Force-stopped and relaunched only EnergyCore; authenticated plan catalog returned without another login. `/api/v1/auth/me` from Dart returned HTTP 200 at 01:37:12 UTC. Android session restoration is VERIFIED.
- No plan was purchased. The synthetic account correctly remains on the plan-selection screen; device/energy modules require the product's subscription entitlement.
- Earlier install failures were caused by incomplete Android startup. Readiness must include boot completion and a stopped boot animation, not only an available package service.

## Neon persistence acceptance

- Script: `scripts/verify-cloud-persistence.ps1`; machine-readable result: `scripts/persistence-result.local.json`.
- Verified dedicated endpoint `ep-super-frost-auww7roc-pooler` and database `neondb` before the test. No other Neon project was used.
- Registered a unique synthetic record on `energycore-platform-00001-pc7`, then routed traffic to `energycore-platform-persist-0907012918` using the same image and unchanged database/secret references and capacity.
- After the revision change, login succeeded and `/users/me` returned the same user ID (5), email and stored name marker. Persistence across revisions is VERIFIED.
- The first preparation check incorrectly relied on `latestReadyRevisionName` for a zero-traffic revision. It stopped before switching traffic. The check now reads the prepared revision's Ready condition, and the successful continuation reused that revision.
- Min instances remain 0, service maximum 1, 1 CPU, 512 MiB, concurrency 10, CPU throttling true, startup CPU boost false. The old revision is retained for rollback; no image was rebuilt.

## Scope and retained limitations

- iOS compilation remains unverified; no App Store publication is claimed.
- No full Maven suite rerun is claimed. Original local integration evidence remains separate.
- The legacy temporary transfer key `C:\Users\Asus\AppData\Local\Temp\energycore-neon-transfer-key.xml` still exists. Its deletion was previously rejected by policy; it was not read, reused or retried through another mechanism. This cleanup remains blocked separately from deployment acceptance.

Canva, presentation captures and application source were preserved. No commit, push, new image build, subscription purchase or additional billing linkage was performed in this continuation. Scale-to-zero and the instance cap do not guarantee a zero bill.
