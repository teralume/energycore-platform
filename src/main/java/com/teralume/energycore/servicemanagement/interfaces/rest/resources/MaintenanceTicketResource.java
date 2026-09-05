package com.teralume.energycore.servicemanagement.interfaces.rest.resources;

import com.teralume.energycore.servicemanagement.domain.model.aggregates.MaintenanceTicket;

import java.time.LocalDate;

public record MaintenanceTicketResource(
        Long id,
        Long userId,
        Long deviceId,
        String deviceName,
        String type,
        String description,
        LocalDate scheduledDate,
        String status
) {
    public static MaintenanceTicketResource from(MaintenanceTicket ticket) {
        return new MaintenanceTicketResource(
                ticket.getId(),
                ticket.getUserId(),
                ticket.getDeviceId(),
                ticket.getDeviceName(),
                ticket.getType(),
                ticket.getDescription(),
                ticket.getScheduledDate(),
                ticket.getStatus()
        );
    }
}