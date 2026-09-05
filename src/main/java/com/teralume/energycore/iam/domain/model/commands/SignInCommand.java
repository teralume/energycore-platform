package com.teralume.energycore.iam.domain.model.commands;

public record SignInCommand(
        String email,
        String password
) {
}