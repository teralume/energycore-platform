package com.teralume.energycore.billing.application.gateways;

public interface PaymentGatewayPort {
    PaymentGatewayChargeResult charge(PaymentGatewayChargeRequest request);
}
