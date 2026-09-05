package com.teralume.energycore.devicecontrol.interfaces.rest.transform;

import com.teralume.energycore.devicecontrol.domain.model.commands.CreateDeviceGroupCommand;
import com.teralume.energycore.devicecontrol.interfaces.rest.resources.CreateDeviceGroupResource;

public class CreateDeviceGroupCommandFromResourceAssembler {
    public static CreateDeviceGroupCommand toCommandFromResource(CreateDeviceGroupResource resource, Long userId) {
        return new CreateDeviceGroupCommand(
                userId,
                resource.name(),
                resource.description(),
                resource.deviceIds()
        );
    }
}
