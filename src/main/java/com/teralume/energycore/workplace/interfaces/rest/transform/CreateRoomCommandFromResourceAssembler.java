package com.teralume.energycore.workplace.interfaces.rest.transform;

import com.teralume.energycore.workplace.domain.model.commands.CreateRoomCommand;
import com.teralume.energycore.workplace.interfaces.rest.resources.CreateRoomResource;

public class CreateRoomCommandFromResourceAssembler {
    private CreateRoomCommandFromResourceAssembler() {
    }

    public static CreateRoomCommand toCommandFromResource(CreateRoomResource resource) {
        return new CreateRoomCommand(resource.locationId(), resource.name(), resource.floor());
    }
}