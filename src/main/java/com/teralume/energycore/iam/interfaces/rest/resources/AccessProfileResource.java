package com.teralume.energycore.iam.interfaces.rest.resources;

import com.teralume.energycore.iam.domain.model.AccessPermission;

import java.util.Set;

public record AccessProfileResource(
        Long id,
        String name,
        String description,
        Set<AccessPermission> permissions
) {
}
