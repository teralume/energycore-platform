package com.teralume.energycore.reporting.interfaces.rest.resources;

import com.teralume.energycore.shared.domain.valueobjects.UserPlatformSummary;

import java.math.BigDecimal;
import java.util.List;

public record PlatformSummaryResource(
        Long userId,
        boolean operationallyHealthy,
        BigDecimal platformScore,
        BillingSummary billing,
        DeviceSummary devices,
        WorkplaceSummary workplace,
        EnergySummary energy,
        NotificationSummary notifications,
        ServiceSummary service,
        List<ActionSummary> suggestedActions
) {

    public static PlatformSummaryResource from(UserPlatformSummary summary) {
        return new PlatformSummaryResource(
                summary.getUserId().getValue(),
                summary.isOperationallyHealthy(),
                summary.calculatePlatformScore(),
                BillingSummary.from(summary.getBilling()),
                DeviceSummary.from(summary.getDevices()),
                WorkplaceSummary.from(summary.getWorkplace()),
                EnergySummary.from(summary.getEnergy()),
                NotificationSummary.from(summary.getNotifications()),
                ServiceSummary.from(summary.getService()),
                summary.getSuggestedActions().stream()
                        .map(ActionSummary::from)
                        .toList()
        );
    }

    public record BillingSummary(
            boolean active,
            String planCode,
            String planName,
            Integer maxDevices,
            Integer maxRoutines,
            Integer maxAlerts,
            Boolean reportExportEnabled
    ) {
        public static BillingSummary from(UserPlatformSummary.BillingSnapshot snapshot) {
            return new BillingSummary(
                    snapshot.active(),
                    snapshot.planCode(),
                    snapshot.planName(),
                    snapshot.maxDevices(),
                    snapshot.maxRoutines(),
                    snapshot.maxAlerts(),
                    snapshot.reportExportEnabled()
            );
        }
    }

    public record DeviceSummary(
            int totalDevices,
            int onlineDevices,
            int offlineDevices,
            int routines,
            int groups
    ) {
        public static DeviceSummary from(UserPlatformSummary.DeviceSnapshot snapshot) {
            return new DeviceSummary(
                    snapshot.totalDevices(),
                    snapshot.onlineDevices(),
                    snapshot.offlineDevices(),
                    snapshot.routines(),
                    snapshot.groups()
            );
        }
    }

    public record WorkplaceSummary(
            int locations,
            int rooms,
            int assignments,
            int unassignedDevices
    ) {
        public static WorkplaceSummary from(UserPlatformSummary.WorkplaceSnapshot snapshot) {
            return new WorkplaceSummary(
                    snapshot.locations(),
                    snapshot.rooms(),
                    snapshot.assignments(),
                    snapshot.unassignedDevices()
            );
        }
    }

    public record EnergySummary(
            BigDecimal totalWatts,
            BigDecimal averageWatts,
            BigDecimal highestWatts,
            String usageLevel,
            Long highestDeviceId,
            String highestDeviceName
    ) {
        public static EnergySummary from(UserPlatformSummary.EnergySnapshot snapshot) {
            return new EnergySummary(
                    snapshot.totalWatts().getWatts(),
                    snapshot.averageWatts().getWatts(),
                    snapshot.highestWatts().getWatts(),
                    snapshot.usageLevel(),
                    snapshot.highestDeviceId(),
                    snapshot.highestDeviceName()
            );
        }
    }

    public record NotificationSummary(
            int totalAlerts,
            int unreadAlerts,
            int criticalAlerts,
            int alertRules
    ) {
        public static NotificationSummary from(UserPlatformSummary.NotificationSnapshot snapshot) {
            return new NotificationSummary(
                    snapshot.totalAlerts(),
                    snapshot.unreadAlerts(),
                    snapshot.criticalAlerts(),
                    snapshot.alertRules()
            );
        }
    }

    public record ServiceSummary(
            int supportTickets,
            int maintenanceTickets,
            int openTickets,
            int scheduledMaintenances
    ) {
        public static ServiceSummary from(UserPlatformSummary.ServiceSnapshot snapshot) {
            return new ServiceSummary(
                    snapshot.supportTickets(),
                    snapshot.maintenanceTickets(),
                    snapshot.openTickets(),
                    snapshot.scheduledMaintenances()
            );
        }
    }

    public record ActionSummary(
            String code,
            String message,
            String targetSection
    ) {
        public static ActionSummary from(UserPlatformSummary.PlatformAction action) {
            return new ActionSummary(
                    action.code(),
                    action.message(),
                    action.targetSection()
            );
        }
    }
}