package com.teralume.energycore.notifications.domain.model.commands;

public record MarkAlertAsReadCommand(
        Long userId,
        Long alertId
) {
}