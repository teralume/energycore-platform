package com.teralume.energycore.devicecontrol.interfaces.rest.transform;

import com.teralume.energycore.devicecontrol.domain.model.commands.ExecuteGroupActionCommand;
import com.teralume.energycore.devicecontrol.interfaces.rest.resources.ExecuteGroupActionResource;

public class ExecuteGroupActionCommandFromResourceAssembler {
    public static ExecuteGroupActionCommand toCommandFromResource(ExecuteGroupActionResource resource) {
        return new ExecuteGroupActionCommand(resource.status());
    }
}