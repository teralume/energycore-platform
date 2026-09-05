package com.teralume.energycore.servicemanagement.interfaces.rest.transform;

import com.teralume.energycore.servicemanagement.domain.model.commands.CreateSupportTicketCommand;
import com.teralume.energycore.servicemanagement.interfaces.rest.resources.CreateSupportTicketResource;

public class CreateSupportTicketCommandFromResourceAssembler {
    public static CreateSupportTicketCommand toCommandFromResource(CreateSupportTicketResource resource, Long userId) {
        return new CreateSupportTicketCommand(
                userId,
                resource.subject(),
                resource.description(),
                resource.priority()
        );
    }
}
