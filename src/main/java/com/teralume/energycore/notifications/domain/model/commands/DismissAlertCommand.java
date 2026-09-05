package com.teralume.energycore.notifications.domain.model.commands;

public record DismissAlertCommand(
        Long userId,
        Long alertId,
        Integer minutes
) {
}
