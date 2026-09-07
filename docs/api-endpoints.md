# EnergyCore REST API endpoint inventory

Generated from Spring MVC controller annotations at 2026-09-06T04:13:39.020Z.

Total documented operations: **100**.

Runtime OpenAPI sources:

- Swagger UI: `/swagger-ui.html`
- OpenAPI JSON: `/v3/api-docs`

| Method | Route | Controller | Source |
|:--|:--|:--|:--|
| GET | `/` | HealthController | `src/main/java/com/teralume/energycore/shared/interfaces/rest/controllers/HealthController.java` |
| GET | `/api/v1/access-profiles` | AccessProfileController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/AccessProfileController.java` |
| GET | `/api/v1/alerts` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| POST | `/api/v1/alerts` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| DELETE | `/api/v1/alerts/{alertId}` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| PATCH | `/api/v1/alerts/{alertId}/dismiss` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| PATCH | `/api/v1/alerts/{alertId}/read` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| PATCH | `/api/v1/alerts/{alertId}/resolve` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| GET | `/api/v1/alerts/rule-profiles` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| POST | `/api/v1/alerts/rule-profiles` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| PATCH | `/api/v1/alerts/rule-profiles/{profileId}/activate` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| GET | `/api/v1/alerts/rules` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| POST | `/api/v1/alerts/rules` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| DELETE | `/api/v1/alerts/rules/{ruleId}` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| PATCH | `/api/v1/alerts/rules/{ruleId}/toggle` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| POST | `/api/v1/alerts/rules/evaluate` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| GET | `/api/v1/auth/me` | AuthController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/AuthController.java` |
| POST | `/api/v1/auth/recover-password` | AuthController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/AuthController.java` |
| POST | `/api/v1/auth/reset-password` | AuthController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/AuthController.java` |
| POST | `/api/v1/auth/sign-in` | AuthController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/AuthController.java` |
| POST | `/api/v1/auth/sign-out` | AuthController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/AuthController.java` |
| POST | `/api/v1/auth/sign-up` | AuthController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/AuthController.java` |
| GET | `/api/v1/billing/invoices` | BillingController | `src/main/java/com/teralume/energycore/billing/interfaces/rest/controllers/BillingController.java` |
| GET | `/api/v1/billing/payments` | BillingController | `src/main/java/com/teralume/energycore/billing/interfaces/rest/controllers/BillingController.java` |
| POST | `/api/v1/billing/payments` | BillingController | `src/main/java/com/teralume/energycore/billing/interfaces/rest/controllers/BillingController.java` |
| GET | `/api/v1/billing/plans` | BillingController | `src/main/java/com/teralume/energycore/billing/interfaces/rest/controllers/BillingController.java` |
| POST | `/api/v1/billing/subscriptions` | BillingController | `src/main/java/com/teralume/energycore/billing/interfaces/rest/controllers/BillingController.java` |
| POST | `/api/v1/billing/subscriptions/checkout` | BillingController | `src/main/java/com/teralume/energycore/billing/interfaces/rest/controllers/BillingController.java` |
| DELETE | `/api/v1/billing/subscriptions/current` | BillingController | `src/main/java/com/teralume/energycore/billing/interfaces/rest/controllers/BillingController.java` |
| GET | `/api/v1/billing/subscriptions/current` | BillingController | `src/main/java/com/teralume/energycore/billing/interfaces/rest/controllers/BillingController.java` |
| GET | `/api/v1/device-groups` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| POST | `/api/v1/device-groups` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| DELETE | `/api/v1/device-groups/{groupId}` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| PATCH | `/api/v1/device-groups/{groupId}` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| PATCH | `/api/v1/device-groups/{groupId}/execute` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| GET | `/api/v1/devices` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| POST | `/api/v1/devices` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| DELETE | `/api/v1/devices/{deviceId}` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| PATCH | `/api/v1/devices/{deviceId}` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| PATCH | `/api/v1/devices/{deviceId}/status` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| PATCH | `/api/v1/devices/{deviceId}/toggle` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| POST | `/api/v1/devices/pairings` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| GET | `/api/v1/energy-readings` | EnergyMonitoringController | `src/main/java/com/teralume/energycore/energymonitoring/interfaces/rest/controllers/EnergyMonitoringController.java` |
| POST | `/api/v1/energy-readings` | EnergyMonitoringController | `src/main/java/com/teralume/energycore/energymonitoring/interfaces/rest/controllers/EnergyMonitoringController.java` |
| GET | `/api/v1/energy-readings/dashboard-summary` | EnergyMonitoringController | `src/main/java/com/teralume/energycore/energymonitoring/interfaces/rest/controllers/EnergyMonitoringController.java` |
| GET | `/api/v1/energy-readings/sampling-settings` | EnergyMonitoringController | `src/main/java/com/teralume/energycore/energymonitoring/interfaces/rest/controllers/EnergyMonitoringController.java` |
| PATCH | `/api/v1/energy-readings/sampling-settings` | EnergyMonitoringController | `src/main/java/com/teralume/energycore/energymonitoring/interfaces/rest/controllers/EnergyMonitoringController.java` |
| GET | `/api/v1/health` | HealthController | `src/main/java/com/teralume/energycore/shared/interfaces/rest/controllers/HealthController.java` |
| GET | `/api/v1/maintenance-tickets` | ServiceManagementController | `src/main/java/com/teralume/energycore/servicemanagement/interfaces/rest/controllers/ServiceManagementController.java` |
| POST | `/api/v1/maintenance-tickets` | ServiceManagementController | `src/main/java/com/teralume/energycore/servicemanagement/interfaces/rest/controllers/ServiceManagementController.java` |
| DELETE | `/api/v1/maintenance-tickets/{ticketId}` | ServiceManagementController | `src/main/java/com/teralume/energycore/servicemanagement/interfaces/rest/controllers/ServiceManagementController.java` |
| PATCH | `/api/v1/maintenance-tickets/{ticketId}/status` | ServiceManagementController | `src/main/java/com/teralume/energycore/servicemanagement/interfaces/rest/controllers/ServiceManagementController.java` |
| GET | `/api/v1/notifications/preferences` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| PUT | `/api/v1/notifications/preferences` | NotificationController | `src/main/java/com/teralume/energycore/notifications/interfaces/rest/controllers/NotificationController.java` |
| GET | `/api/v1/operation-modes` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| POST | `/api/v1/operation-modes` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| DELETE | `/api/v1/operation-modes/{modeId}` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| PATCH | `/api/v1/operation-modes/{modeId}/activate` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| GET | `/api/v1/operation-modes/{modeId}/preview` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| GET | `/api/v1/reporting/platform/summary` | PlatformInsightController | `src/main/java/com/teralume/energycore/reporting/interfaces/rest/controllers/PlatformInsightController.java` |
| GET | `/api/v1/reports` | ReportingController | `src/main/java/com/teralume/energycore/reporting/interfaces/rest/controllers/ReportingController.java` |
| POST | `/api/v1/reports` | ReportingController | `src/main/java/com/teralume/energycore/reporting/interfaces/rest/controllers/ReportingController.java` |
| DELETE | `/api/v1/reports/{reportId}` | ReportingController | `src/main/java/com/teralume/energycore/reporting/interfaces/rest/controllers/ReportingController.java` |
| GET | `/api/v1/reports/activity` | ReportingController | `src/main/java/com/teralume/energycore/reporting/interfaces/rest/controllers/ReportingController.java` |
| GET | `/api/v1/reports/energy-goals` | ReportingController | `src/main/java/com/teralume/energycore/reporting/interfaces/rest/controllers/ReportingController.java` |
| POST | `/api/v1/reports/energy-goals` | ReportingController | `src/main/java/com/teralume/energycore/reporting/interfaces/rest/controllers/ReportingController.java` |
| DELETE | `/api/v1/reports/energy-goals/{goalId}` | ReportingController | `src/main/java/com/teralume/energycore/reporting/interfaces/rest/controllers/ReportingController.java` |
| PATCH | `/api/v1/reports/energy-goals/{goalId}` | ReportingController | `src/main/java/com/teralume/energycore/reporting/interfaces/rest/controllers/ReportingController.java` |
| GET | `/api/v1/routines` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| POST | `/api/v1/routines` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| DELETE | `/api/v1/routines/{routineId}` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| PATCH | `/api/v1/routines/{routineId}/execute` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| PATCH | `/api/v1/routines/{routineId}/status` | DeviceControlController | `src/main/java/com/teralume/energycore/devicecontrol/interfaces/rest/controllers/DeviceControlController.java` |
| GET | `/api/v1/support-tickets` | ServiceManagementController | `src/main/java/com/teralume/energycore/servicemanagement/interfaces/rest/controllers/ServiceManagementController.java` |
| POST | `/api/v1/support-tickets` | ServiceManagementController | `src/main/java/com/teralume/energycore/servicemanagement/interfaces/rest/controllers/ServiceManagementController.java` |
| DELETE | `/api/v1/support-tickets/{ticketId}` | ServiceManagementController | `src/main/java/com/teralume/energycore/servicemanagement/interfaces/rest/controllers/ServiceManagementController.java` |
| PATCH | `/api/v1/support-tickets/{ticketId}/status` | ServiceManagementController | `src/main/java/com/teralume/energycore/servicemanagement/interfaces/rest/controllers/ServiceManagementController.java` |
| GET | `/api/v1/users` | UserController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/UserController.java` |
| DELETE | `/api/v1/users/{userId}` | UserController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/UserController.java` |
| PATCH | `/api/v1/users/{userId}/access-profile` | UserController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/UserController.java` |
| GET | `/api/v1/users/{userId}/profile` | UserController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/UserController.java` |
| PUT | `/api/v1/users/{userId}/profile` | UserController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/UserController.java` |
| DELETE | `/api/v1/users/me` | UserController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/UserController.java` |
| GET | `/api/v1/users/me` | UserController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/UserController.java` |
| PUT | `/api/v1/users/me` | UserController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/UserController.java` |
| GET | `/api/v1/users/me/ui-preferences` | UserController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/UserController.java` |
| PUT | `/api/v1/users/me/ui-preferences` | UserController | `src/main/java/com/teralume/energycore/iam/interfaces/rest/controllers/UserController.java` |
| GET | `/api/v1/workplace/device-assignments` | WorkplaceController | `src/main/java/com/teralume/energycore/workplace/interfaces/rest/controllers/WorkplaceController.java` |
| POST | `/api/v1/workplace/device-assignments` | WorkplaceController | `src/main/java/com/teralume/energycore/workplace/interfaces/rest/controllers/WorkplaceController.java` |
| DELETE | `/api/v1/workplace/device-assignments/{assignmentId}` | WorkplaceController | `src/main/java/com/teralume/energycore/workplace/interfaces/rest/controllers/WorkplaceController.java` |
| PATCH | `/api/v1/workplace/device-assignments/{assignmentId}` | WorkplaceController | `src/main/java/com/teralume/energycore/workplace/interfaces/rest/controllers/WorkplaceController.java` |
| GET | `/api/v1/workplace/locations` | WorkplaceController | `src/main/java/com/teralume/energycore/workplace/interfaces/rest/controllers/WorkplaceController.java` |
| POST | `/api/v1/workplace/locations` | WorkplaceController | `src/main/java/com/teralume/energycore/workplace/interfaces/rest/controllers/WorkplaceController.java` |
| DELETE | `/api/v1/workplace/locations/{locationId}` | WorkplaceController | `src/main/java/com/teralume/energycore/workplace/interfaces/rest/controllers/WorkplaceController.java` |
| PATCH | `/api/v1/workplace/locations/{locationId}` | WorkplaceController | `src/main/java/com/teralume/energycore/workplace/interfaces/rest/controllers/WorkplaceController.java` |
| GET | `/api/v1/workplace/rooms` | WorkplaceController | `src/main/java/com/teralume/energycore/workplace/interfaces/rest/controllers/WorkplaceController.java` |
| POST | `/api/v1/workplace/rooms` | WorkplaceController | `src/main/java/com/teralume/energycore/workplace/interfaces/rest/controllers/WorkplaceController.java` |
| DELETE | `/api/v1/workplace/rooms/{roomId}` | WorkplaceController | `src/main/java/com/teralume/energycore/workplace/interfaces/rest/controllers/WorkplaceController.java` |
| PATCH | `/api/v1/workplace/rooms/{roomId}` | WorkplaceController | `src/main/java/com/teralume/energycore/workplace/interfaces/rest/controllers/WorkplaceController.java` |
| GET | `/health` | HealthController | `src/main/java/com/teralume/energycore/shared/interfaces/rest/controllers/HealthController.java` |

> This inventory proves the routes declared in source. A successful runtime request to `/v3/api-docs` is still required before claiming the deployed API contract is operational.
