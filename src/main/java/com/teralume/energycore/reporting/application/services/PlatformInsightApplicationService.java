package com.teralume.energycore.reporting.application.services;

import com.teralume.energycore.billing.domain.model.aggregates.Subscription;
import com.teralume.energycore.billing.domain.model.SubscriptionStatus;
import com.teralume.energycore.billing.domain.repositories.SubscriptionRepository;
import com.teralume.energycore.devicecontrol.domain.model.aggregates.Device;
import com.teralume.energycore.devicecontrol.domain.model.DeviceStatus;
import com.teralume.energycore.devicecontrol.domain.model.aggregates.DeviceGroup;
import com.teralume.energycore.devicecontrol.domain.model.aggregates.Routine;
import com.teralume.energycore.devicecontrol.domain.repositories.DeviceGroupRepository;
import com.teralume.energycore.devicecontrol.domain.repositories.DeviceRepository;
import com.teralume.energycore.devicecontrol.domain.repositories.RoutineRepository;
import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;
import com.teralume.energycore.energymonitoring.domain.repositories.EnergyReadingRepository;
import com.teralume.energycore.notifications.domain.model.aggregates.Alert;
import com.teralume.energycore.notifications.domain.repositories.AlertRepository;
import com.teralume.energycore.notifications.domain.repositories.AlertRuleRepository;
import com.teralume.energycore.shared.domain.valueobjects.UserPlatformSummary;
import com.teralume.energycore.servicemanagement.domain.model.aggregates.MaintenanceTicket;
import com.teralume.energycore.servicemanagement.domain.model.aggregates.SupportTicket;
import com.teralume.energycore.servicemanagement.domain.repositories.MaintenanceTicketRepository;
import com.teralume.energycore.servicemanagement.domain.repositories.SupportTicketRepository;
import com.teralume.energycore.shared.domain.valueobjects.EnergyUsage;
import com.teralume.energycore.shared.domain.valueobjects.UserId;
import com.teralume.energycore.workplace.domain.model.aggregates.DeviceAssignment;
import com.teralume.energycore.workplace.domain.model.aggregates.Location;
import com.teralume.energycore.workplace.domain.model.aggregates.Room;
import com.teralume.energycore.workplace.domain.repositories.DeviceAssignmentRepository;
import com.teralume.energycore.workplace.domain.repositories.LocationRepository;
import com.teralume.energycore.workplace.domain.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlatformInsightApplicationService {

    private final SubscriptionRepository subscriptionRepository;

    private final DeviceRepository deviceRepository;
    private final RoutineRepository routineRepository;
    private final DeviceGroupRepository deviceGroupRepository;

    private final EnergyReadingRepository energyReadingRepository;

    private final LocationRepository locationRepository;
    private final RoomRepository roomRepository;
    private final DeviceAssignmentRepository deviceAssignmentRepository;

    private final AlertRepository alertRepository;
    private final AlertRuleRepository alertRuleRepository;

    private final SupportTicketRepository supportTicketRepository;
    private final MaintenanceTicketRepository maintenanceTicketRepository;

    @Transactional(readOnly = true)
    public UserPlatformSummary getPlatformSummary(Long userId) {
        UserId validUserId = UserId.of(userId);

        List<Device> devices = deviceRepository.findByUserId(userId);
        List<Routine> routines = routineRepository.findByUserId(userId);
        List<DeviceGroup> groups = deviceGroupRepository.findByUserId(userId);

        List<EnergyReading> readings = energyReadingRepository.findByUserIdOrderByRecordedAtDesc(userId);

        List<Location> locations = locationRepository.findByUserId(userId);
        List<Room> rooms = findRoomsFromLocations(locations);
        List<DeviceAssignment> assignments = findAssignmentsFromLocations(locations);

        List<Alert> alerts = alertRepository.findByUserId(userId);

        List<SupportTicket> supportTickets = supportTicketRepository.findByUserId(userId);
        List<MaintenanceTicket> maintenanceTickets = maintenanceTicketRepository.findByUserId(userId);

        return UserPlatformSummary.assemble(
                validUserId,
                buildBillingSnapshot(userId),
                buildDeviceSnapshot(devices, routines, groups),
                buildWorkplaceSnapshot(devices, locations, rooms, assignments),
                buildEnergySnapshot(readings),
                buildNotificationSnapshot(userId, alerts),
                buildServiceSnapshot(supportTickets, maintenanceTickets)
        );
    }

    private UserPlatformSummary.BillingSnapshot buildBillingSnapshot(Long userId) {
        Optional<Subscription> activeSubscription = subscriptionRepository
                .findFirstByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE);

        if (activeSubscription.isEmpty()) {
            return new UserPlatformSummary.BillingSnapshot(
                    false,
                    null,
                    "No active plan",
                    0,
                    0,
                    0,
                    false
            );
        }

        Subscription subscription = activeSubscription.get();

        return new UserPlatformSummary.BillingSnapshot(
                subscription.isActive(),
                subscription.getPlan().getCode(),
                subscription.getPlan().getName(),
                subscription.getPlan().getMaxDevices(),
                subscription.getPlan().getMaxRoutines(),
                subscription.getPlan().getMaxAlerts(),
                subscription.getPlan().getReportExportEnabled()
        );
    }

    private UserPlatformSummary.DeviceSnapshot buildDeviceSnapshot(
            List<Device> devices,
            List<Routine> routines,
            List<DeviceGroup> groups
    ) {
        int onlineDevices = (int) devices.stream()
                .filter(device -> device.getStatus() == DeviceStatus.ON)
                .count();

        int offlineDevices = Math.max(devices.size() - onlineDevices, 0);

        return new UserPlatformSummary.DeviceSnapshot(
                devices.size(),
                onlineDevices,
                offlineDevices,
                routines.size(),
                groups.size()
        );
    }

    private UserPlatformSummary.WorkplaceSnapshot buildWorkplaceSnapshot(
            List<Device> devices,
            List<Location> locations,
            List<Room> rooms,
            List<DeviceAssignment> assignments
    ) {
        Set<Long> assignedDeviceIds = assignments.stream()
                .map(DeviceAssignment::getDeviceId)
                .collect(Collectors.toSet());

        int unassignedDevices = (int) devices.stream()
                .filter(device -> !assignedDeviceIds.contains(device.getId()))
                .count();

        return new UserPlatformSummary.WorkplaceSnapshot(
                locations.size(),
                rooms.size(),
                assignments.size(),
                unassignedDevices
        );
    }

    private UserPlatformSummary.EnergySnapshot buildEnergySnapshot(List<EnergyReading> readings) {
        if (readings.isEmpty()) {
            return new UserPlatformSummary.EnergySnapshot(
                    EnergyUsage.zero(),
                    EnergyUsage.zero(),
                    EnergyUsage.zero(),
                    "LOW",
                    null,
                    null
            );
        }

        BigDecimal totalWatts = readings.stream()
                .map(EnergyReading::getWatts)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageWatts = totalWatts.divide(
                BigDecimal.valueOf(readings.size()),
                2,
                RoundingMode.HALF_UP
        );

        EnergyReading highestReading = readings.stream()
                .max(Comparator.comparing(EnergyReading::getWatts))
                .orElse(null);

        BigDecimal highestWatts = highestReading == null
                ? BigDecimal.ZERO
                : highestReading.getWatts();

        EnergyUsage totalUsage = EnergyUsage.of(totalWatts);

        return new UserPlatformSummary.EnergySnapshot(
                totalUsage,
                EnergyUsage.of(averageWatts),
                EnergyUsage.of(highestWatts),
                totalUsage.classify(),
                highestReading == null ? null : highestReading.getDeviceId(),
                highestReading == null ? null : highestReading.getDeviceName()
        );
    }

    private UserPlatformSummary.NotificationSnapshot buildNotificationSnapshot(
            Long userId,
            List<Alert> alerts
    ) {
        int unreadAlerts = (int) alerts.stream()
                .filter(alert -> !Boolean.TRUE.equals(alert.getReadStatus()))
                .count();

        int criticalAlerts = (int) alerts.stream()
                .filter(alert -> "CRITICAL".equalsIgnoreCase(alert.getLevel()))
                .count();

        int rules = alertRuleRepository.findByUserId(userId).size();

        return new UserPlatformSummary.NotificationSnapshot(
                alerts.size(),
                unreadAlerts,
                criticalAlerts,
                rules
        );
    }

    private UserPlatformSummary.ServiceSnapshot buildServiceSnapshot(
            List<SupportTicket> supportTickets,
            List<MaintenanceTicket> maintenanceTickets
    ) {
        int openSupportTickets = (int) supportTickets.stream()
                .filter(ticket -> !"CLOSED".equalsIgnoreCase(ticket.getStatus())
                        && !"COMPLETED".equalsIgnoreCase(ticket.getStatus())
                        && !"CANCELED".equalsIgnoreCase(ticket.getStatus()))
                .count();

        int openMaintenanceTickets = (int) maintenanceTickets.stream()
                .filter(ticket -> !"COMPLETED".equalsIgnoreCase(ticket.getStatus())
                        && !"CANCELED".equalsIgnoreCase(ticket.getStatus()))
                .count();

        int scheduledMaintenances = (int) maintenanceTickets.stream()
                .filter(ticket -> "SCHEDULED".equalsIgnoreCase(ticket.getStatus()))
                .count();

        return new UserPlatformSummary.ServiceSnapshot(
                supportTickets.size(),
                maintenanceTickets.size(),
                openSupportTickets + openMaintenanceTickets,
                scheduledMaintenances
        );
    }

    private List<Room> findRoomsFromLocations(List<Location> locations) {
        return locations.stream()
                .flatMap(location -> roomRepository.findByLocationId(location.getId()).stream())
                .toList();
    }

    private List<DeviceAssignment> findAssignmentsFromLocations(List<Location> locations) {
        return locations.stream()
                .flatMap(location -> deviceAssignmentRepository.findByLocationId(location.getId()).stream())
                .toList();
    }
}