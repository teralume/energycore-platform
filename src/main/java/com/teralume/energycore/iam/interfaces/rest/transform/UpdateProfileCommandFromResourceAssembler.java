package com.teralume.energycore.iam.interfaces.rest.transform;

import com.teralume.energycore.iam.domain.model.commands.UpdateProfileCommand;
import com.teralume.energycore.iam.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResourceAssembler {
    private UpdateProfileCommandFromResourceAssembler() {
    }

    public static UpdateProfileCommand toCommandFromResource(UpdateProfileResource resource) {
        return new UpdateProfileCommand(resource.fullName(), resource.email());
    }
}