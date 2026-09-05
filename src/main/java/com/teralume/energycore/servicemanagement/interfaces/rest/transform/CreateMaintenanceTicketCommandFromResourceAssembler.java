package com.teralume.energycore.servicemanagement.interfaces.rest.transform;

import com.teralume.energycore.servicemanagement.domain.model.commands.CreateMaintenanceTicketCommand;
import com.teralume.energycore.servicemanagement.interfaces.rest.resources.CreateMaintenanceTicketResource;

public class CreateMaintenanceTicketCommandFromResourceAssembler {
    public static CreateMaintenanceTicketCommand toCommandFromResource(CreateMaintenanceTicketResource resource, Long userId) {
        return new CreateMaintenanceTicketCommand(
                userId,
                resource.deviceId(),
                resource.deviceName(),
                resource.type(),
                resource.description(),
                resource.scheduledDate()
        );
    }
}
