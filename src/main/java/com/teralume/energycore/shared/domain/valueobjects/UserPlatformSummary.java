package com.teralume.energycore.shared.domain.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.EnergyUsage;
import com.teralume.energycore.shared.domain.valueobjects.UserId;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
public class UserPlatformSummary {

    private final UserId userId;
    private final BillingSnapshot billing;
    private final DeviceSnapshot devices;
    private final WorkplaceSnapshot workplace;
    private final EnergySnapshot energy;
    private final NotificationSnapshot notifications;
    private final ServiceSnapshot service;
    private final List<PlatformAction> suggestedActions;

    private UserPlatformSummary(
            UserId userId,
            BillingSnapshot billing,
            DeviceSnapshot devices,
            WorkplaceSnapshot workplace,
            EnergySnapshot energy,
            NotificationSnapshot notifications,
            ServiceSnapshot service
    ) {
        this.userId = userId;
        this.billing = billing;
        this.devices = devices;
        this.workplace = workplace;
        this.energy = energy;
        this.notifications = notifications;
        this.service = service;
        this.suggestedActions = buildSuggestedActions();
    }

    public static UserPlatformSummary assemble(
            UserId userId,
            BillingSnapshot billing,
            DeviceSnapshot devices,
            WorkplaceSnapshot workplace,
            EnergySnapshot energy,
            NotificationSnapshot notifications,
            ServiceSnapshot service
    ) {
        return new UserPlatformSummary(
                userId,
                billing,
                devices,
                workplace,
                energy,
                notifications,
                service
        );
    }

    public boolean isOperationallyHealthy() {
        return billing.active()
                && notifications.criticalAlerts() == 0
                && service.openTickets() == 0
                && !"HIGH".equalsIgnoreCase(energy.usageLevel());
    }

    public BigDecimal calculatePlatformScore() {
        BigDecimal score = new BigDecimal("100");

        if (!billing.active()) {
            score = score.subtract(new BigDecimal("30"));
        }

        score = score.subtract(BigDecimal.valueOf(notifications.criticalAlerts()).multiply(new BigDecimal("8")));
        score = score.subtract(BigDecimal.valueOf(service.openTickets()).multiply(new BigDecimal("4")));

        if ("HIGH".equalsIgnoreCase(energy.usageLevel())) {
            score = score.subtract(new BigDecimal("15"));
        }

        if (devices.offlineDevices() > 0) {
            score = score.subtract(BigDecimal.valueOf(devices.offlineDevices()).multiply(new BigDecimal("3")));
        }

        if (score.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        return score.setScale(2, RoundingMode.HALF_UP);
    }

    private List<PlatformAction> buildSuggestedActions() {
        List<PlatformAction> actions = new java.util.ArrayList<>();

        if (!billing.active()) {
            actions.add(new PlatformAction(
                    "BILLING_REQUIRED",
                    "Activa una suscripciÃ³n para desbloquear reportes, alertas y automatizaciones.",
                    "billing"
            ));
        }

        if (devices.offlineDevices() > 0) {
            actions.add(new PlatformAction(
                    "DEVICE_ATTENTION",
                    "Hay dispositivos desconectados. Revisa Device Control antes de generar nuevas lecturas.",
                    "device-control"
            ));
        }

        if ("HIGH".equalsIgnoreCase(energy.usageLevel())) {
            actions.add(new PlatformAction(
                    "ENERGY_HIGH_USAGE",
                    "El consumo estÃ¡ alto. Revisa Energy Monitoring y crea una meta de ahorro en Reporting.",
                    "energy-monitoring"
            ));
        }

        if (notifications.criticalAlerts() > 0) {
            actions.add(new PlatformAction(
                    "CRITICAL_ALERTS",
                    "Tienes alertas crÃ­ticas. Revisa Notifications antes de cerrar el ciclo operativo.",
                    "notifications"
            ));
        }

        if (service.openTickets() > 0) {
            actions.add(new PlatformAction(
                    "SERVICE_PENDING",
                    "Hay tickets abiertos. Service Management necesita atenciÃ³n.",
                    "service-management"
            ));
        }

        if (workplace.unassignedDevices() > 0) {
            actions.add(new PlatformAction(
                    "WORKPLACE_ASSIGNMENT",
                    "Hay dispositivos sin ubicaciÃ³n. AsÃ­gnalos a una sala para mejorar trazabilidad.",
                    "workplace"
            ));
        }

        if (actions.isEmpty()) {
            actions.add(new PlatformAction(
                    "ALL_CONNECTED",
                    "Todo estÃ¡ conectado y funcionando correctamente.",
                    "dashboard"
            ));
        }

        return actions;
    }

    public record BillingSnapshot(
            boolean active,
            String planCode,
            String planName,
            Integer maxDevices,
            Integer maxRoutines,
            Integer maxAlerts,
            Boolean reportExportEnabled
    ) {
    }

    public record DeviceSnapshot(
            int totalDevices,
            int onlineDevices,
            int offlineDevices,
            int routines,
            int groups
    ) {
    }

    public record WorkplaceSnapshot(
            int locations,
            int rooms,
            int assignments,
            int unassignedDevices
    ) {
    }

    public record EnergySnapshot(
            EnergyUsage totalWatts,
            EnergyUsage averageWatts,
            EnergyUsage highestWatts,
            String usageLevel,
            Long highestDeviceId,
            String highestDeviceName
    ) {
    }

    public record NotificationSnapshot(
            int totalAlerts,
            int unreadAlerts,
            int criticalAlerts,
            int alertRules
    ) {
    }

    public record ServiceSnapshot(
            int supportTickets,
            int maintenanceTickets,
            int openTickets,
            int scheduledMaintenances
    ) {
    }

    public record PlatformAction(
            String code,
            String message,
            String targetSection
    ) {
    }
}