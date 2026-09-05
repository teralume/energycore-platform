package com.teralume.energycore.billing.application.gateways;

public record PaymentGatewayChargeResult(
        boolean approved,
        String provider,
        String transactionId,
        String statusMessage
) {
}
