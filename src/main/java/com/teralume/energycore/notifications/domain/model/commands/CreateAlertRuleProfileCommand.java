package com.teralume.energycore.notifications.domain.model.commands;

public record CreateAlertRuleProfileCommand(
        Long userId,
        String name,
        String description,
        String scopeType,
        String scopeId,
        String mode,
        String sensitivity
) {
}
