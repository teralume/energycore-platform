package com.teralume.energycore.iam.interfaces.rest.transform;

import com.teralume.energycore.iam.domain.model.commands.SignUpCommand;
import com.teralume.energycore.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    private SignUpCommandFromResourceAssembler() {
    }

    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(resource.fullName(), resource.email(), resource.password());
    }
}