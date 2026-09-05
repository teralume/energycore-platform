package com.teralume.energycore.iam.interfaces.rest.resources;

import com.teralume.energycore.iam.domain.model.aggregates.User;

public record UserResource(
        Long id,
        String fullName,
        String email,
        Long accessProfileId,
        String accessProfileName,
        String status
) {
    public static UserResource from(User user) {
        Long profileId = user.getAccessProfile() != null ? user.getAccessProfile().getId() : null;
        String profileName = user.getAccessProfile() != null ? user.getAccessProfile().getName() : null;

        return new UserResource(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                profileId,
                profileName,
                user.getStatus().name()
        );
    }
}