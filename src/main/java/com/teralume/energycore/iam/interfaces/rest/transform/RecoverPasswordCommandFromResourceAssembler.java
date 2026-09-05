package com.teralume.energycore.iam.interfaces.rest.transform;

import com.teralume.energycore.iam.domain.model.commands.RecoverPasswordCommand;
import com.teralume.energycore.iam.interfaces.rest.resources.RecoverPasswordResource;

public class RecoverPasswordCommandFromResourceAssembler {
    private RecoverPasswordCommandFromResourceAssembler() {
    }

    public static RecoverPasswordCommand toCommandFromResource(RecoverPasswordResource resource) {
        return new RecoverPasswordCommand(resource.email());
    }
}