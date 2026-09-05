package com.teralume.energycore.iam.interfaces.rest.transform;

import com.teralume.energycore.iam.application.results.AuthenticationResult;
import com.teralume.energycore.iam.interfaces.rest.resources.AuthResource;

public class AuthResourceFromResultAssembler {
    private AuthResourceFromResultAssembler() {
    }

    public static AuthResource toResourceFromResult(AuthenticationResult result) {
        return new AuthResource(
                UserResourceFromEntityAssembler.toResourceFromEntity(result.user()),
                result.token()
        );
    }
}