package com.teralume.energycore.iam.domain.model.commands;

public record ResetPasswordCommand(
        String token,
        String password
) {
}
