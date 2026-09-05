package com.teralume.energycore.workplace.interfaces.rest.transform;

import com.teralume.energycore.workplace.domain.model.commands.AssignDeviceCommand;
import com.teralume.energycore.workplace.interfaces.rest.resources.AssignDeviceResource;

public class AssignDeviceCommandFromResourceAssembler {
    private AssignDeviceCommandFromResourceAssembler() {
    }

    public static AssignDeviceCommand toCommandFromResource(AssignDeviceResource resource) {
        return new AssignDeviceCommand(resource.deviceId(), resource.roomId(), resource.locationId());
    }
}