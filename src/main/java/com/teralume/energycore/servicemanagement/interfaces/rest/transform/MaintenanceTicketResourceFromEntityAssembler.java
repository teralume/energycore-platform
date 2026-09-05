package com.teralume.energycore.servicemanagement.interfaces.rest.transform;

import com.teralume.energycore.servicemanagement.domain.model.aggregates.MaintenanceTicket;
import com.teralume.energycore.servicemanagement.interfaces.rest.resources.MaintenanceTicketResource;

public class MaintenanceTicketResourceFromEntityAssembler {
    public static MaintenanceTicketResource toResourceFromEntity(MaintenanceTicket ticket) {
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