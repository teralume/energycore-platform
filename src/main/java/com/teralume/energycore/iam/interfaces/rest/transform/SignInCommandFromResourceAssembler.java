package com.teralume.energycore.iam.interfaces.rest.transform;

import com.teralume.energycore.iam.domain.model.commands.SignInCommand;
import com.teralume.energycore.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    private SignInCommandFromResourceAssembler() {
    }

    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.email(), resource.password());
    }
}