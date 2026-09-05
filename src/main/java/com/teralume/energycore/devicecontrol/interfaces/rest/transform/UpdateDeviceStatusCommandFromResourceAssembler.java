package com.teralume.energycore.devicecontrol.interfaces.rest.transform;

import com.teralume.energycore.devicecontrol.domain.model.commands.UpdateDeviceStatusCommand;
import com.teralume.energycore.devicecontrol.interfaces.rest.resources.UpdateDeviceStatusResource;

public class UpdateDeviceStatusCommandFromResourceAssembler {
    public static UpdateDeviceStatusCommand toCommandFromResource(UpdateDeviceStatusResource resource) {
        return new UpdateDeviceStatusCommand(resource.status());
    }
}