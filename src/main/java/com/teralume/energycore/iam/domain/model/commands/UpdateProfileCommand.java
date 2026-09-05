package com.teralume.energycore.iam.domain.model.commands;

public record UpdateProfileCommand(
        String fullName,
        String email
) {
}