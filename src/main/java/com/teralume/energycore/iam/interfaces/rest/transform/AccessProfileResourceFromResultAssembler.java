package com.teralume.energycore.iam.interfaces.rest.transform;

import com.teralume.energycore.iam.application.results.AccessProfileDetails;
import com.teralume.energycore.iam.interfaces.rest.resources.AccessProfileResource;

public class AccessProfileResourceFromResultAssembler {
    private AccessProfileResourceFromResultAssembler() {
    }

    public static AccessProfileResource toResourceFromResult(AccessProfileDetails details) {
        return new AccessProfileResource(
                details.profile().getId(),
                details.profile().getName(),
                details.profile().getDescription(),
                details.permissions()
        );
    }
}
