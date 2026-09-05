package com.teralume.energycore.notifications.domain.model.commands;

public record ToggleAlertRuleCommand(
        Long userId,
        Long ruleId
) {
}