package com.teralume.energycore.billing.domain.model.commands;

public record SubscribeCommand(
        Long userId,
        String planCode
) {
}