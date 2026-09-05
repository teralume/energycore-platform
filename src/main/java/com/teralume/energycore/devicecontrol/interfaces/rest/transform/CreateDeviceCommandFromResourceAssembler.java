package com.teralume.energycore.devicecontrol.interfaces.rest.transform;

import com.teralume.energycore.devicecontrol.domain.model.commands.CreateDeviceCommand;
import com.teralume.energycore.devicecontrol.interfaces.rest.resources.CreateDeviceResource;

public class CreateDeviceCommandFromResourceAssembler {
    public static CreateDeviceCommand toCommandFromResource(CreateDeviceResource resource, Long userId) {
        return new CreateDeviceCommand(
                userId,
                resource.name(),
                resource.room(),
                resource.type(),
                resource.powerWatts()
        );
    }
}
