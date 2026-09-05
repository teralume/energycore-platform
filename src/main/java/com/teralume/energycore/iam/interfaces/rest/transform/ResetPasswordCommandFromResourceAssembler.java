package com.teralume.energycore.iam.interfaces.rest.transform;

import com.teralume.energycore.iam.domain.model.commands.ResetPasswordCommand;
import com.teralume.energycore.iam.interfaces.rest.resources.ResetPasswordResource;

public class ResetPasswordCommandFromResourceAssembler {

    private ResetPasswordCommandFromResourceAssembler() {
    }

    public static ResetPasswordCommand toCommandFromResource(ResetPasswordResource resource) {
        return new ResetPasswordCommand(resource.token(), resource.password());
    }
}
