package com.teralume.energycore.iam.interfaces.rest.resources;

public record AuthResource(
        UserResource user,
        String token
) {
}