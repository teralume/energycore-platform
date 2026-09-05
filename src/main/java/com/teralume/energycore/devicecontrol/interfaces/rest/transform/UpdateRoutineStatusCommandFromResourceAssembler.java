package com.teralume.energycore.devicecontrol.interfaces.rest.transform;

import com.teralume.energycore.devicecontrol.domain.model.commands.UpdateRoutineStatusCommand;
import com.teralume.energycore.devicecontrol.interfaces.rest.resources.UpdateRoutineStatusResource;

public class UpdateRoutineStatusCommandFromResourceAssembler {
    public static UpdateRoutineStatusCommand toCommandFromResource(UpdateRoutineStatusResource resource) {
        return new UpdateRoutineStatusCommand(resource.enabled());
    }
}