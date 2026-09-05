package com.teralume.energycore.billing.domain.model.commands;

public record ProcessPaymentCommand(
        Long userId,
        Long subscriptionId
) {
}