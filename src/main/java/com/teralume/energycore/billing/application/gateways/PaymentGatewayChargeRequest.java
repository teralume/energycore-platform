package com.teralume.energycore.billing.application.gateways;

import com.teralume.energycore.shared.domain.valueobjects.Money;

public record PaymentGatewayChargeRequest(
        Long userId,
        String planCode,
        Money amount,
        String holderName,
        String cardNumber,
        String expirationDate
) {
}
