package com.teralume.energycore.iam.interfaces.rest.transform;

import com.teralume.energycore.iam.domain.model.aggregates.User;
import com.teralume.energycore.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    private UserResourceFromEntityAssembler() {
    }

    public static UserResource toResourceFromEntity(User entity) {
        return UserResource.from(entity);
    }
}