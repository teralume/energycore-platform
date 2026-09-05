package com.teralume.energycore.notifications.domain.model.commands;

public record ActivateAlertRuleProfileCommand(
        Long userId,
        Long profileId
) {
}
