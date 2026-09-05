package com.teralume.energycore.billing.domain.model.commands;

public record CheckoutSubscriptionCommand(
        Long userId,
        String planCode,
        String holderName,
        String cardNumber,
        String expirationDate,
        String cvv
) {
}
