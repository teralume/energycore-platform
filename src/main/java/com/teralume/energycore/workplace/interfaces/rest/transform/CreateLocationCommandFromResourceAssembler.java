package com.teralume.energycore.workplace.interfaces.rest.transform;

import com.teralume.energycore.workplace.domain.model.commands.CreateLocationCommand;
import com.teralume.energycore.workplace.interfaces.rest.resources.CreateLocationResource;

public class CreateLocationCommandFromResourceAssembler {
    private CreateLocationCommandFromResourceAssembler() {
    }

    public static CreateLocationCommand toCommandFromResource(CreateLocationResource resource, Long userId) {
        return new CreateLocationCommand(userId, resource.name(), resource.address(), resource.type());
    }
}
