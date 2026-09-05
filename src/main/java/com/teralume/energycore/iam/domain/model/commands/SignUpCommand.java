package com.teralume.energycore.iam.domain.model.commands;

public record SignUpCommand(
        String fullName,
        String email,
        String password
) {
}