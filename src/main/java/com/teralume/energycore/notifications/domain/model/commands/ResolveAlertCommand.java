package com.teralume.energycore.notifications.domain.model.commands;

public record ResolveAlertCommand(
        Long userId,
        Long alertId
) {
}
