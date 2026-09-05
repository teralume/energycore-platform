package com.teralume.energycore.billing.domain.model.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.Money;

import java.math.BigDecimal;

public record PaymentAmount(Money money) {
    public PaymentAmount(BigDecimal amount) {
        this(Money.of(amount));
    }

    public PaymentAmount {
        if (money == null || money.isZero()) {
            throw new IllegalArgumentException("Payment amount must be greater than zero.");
        }
    }
}