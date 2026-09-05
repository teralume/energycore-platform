package com.teralume.energycore.servicemanagement.interfaces.rest.transform;

import com.teralume.energycore.servicemanagement.domain.model.commands.UpdateTicketStatusCommand;
import com.teralume.energycore.servicemanagement.interfaces.rest.resources.UpdateTicketStatusResource;

public class UpdateTicketStatusCommandFromResourceAssembler {
    public static UpdateTicketStatusCommand toCommandFromResource(UpdateTicketStatusResource resource) {
        return new UpdateTicketStatusCommand(resource.status());
    }
}