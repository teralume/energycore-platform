package com.teralume.energycore.devicecontrol.interfaces.rest.transform;

import com.teralume.energycore.devicecontrol.domain.model.commands.UpdateDeviceGroupCommand;
import com.teralume.energycore.devicecontrol.interfaces.rest.resources.UpdateDeviceGroupResource;

public class UpdateDeviceGroupCommandFromResourceAssembler {
    public static UpdateDeviceGroupCommand toCommandFromResource(UpdateDeviceGroupResource resource) {
        return new UpdateDeviceGroupCommand(
                resource.name(),
                resource.description(),
                resource.deviceIds()
        );
    }
}
