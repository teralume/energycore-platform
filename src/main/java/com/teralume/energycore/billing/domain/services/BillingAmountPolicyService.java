package com.teralume.energycore.billing.domain.services;

import com.teralume.energycore.shared.domain.valueobjects.Money;
import org.springframework.stereotype.Service;

@Service
public class BillingAmountPolicyService {

    public void validatePositiveAmount(Money amount) {
        if (amount == null || !amount.isPositive()) {
            throw new IllegalArgumentException("Billing amount must be positive.");
        }
    }

    public void validateCurrency(Money amount, String expectedCurrency) {
        if (amount == null || amount.getCurrency() == null) {
            throw new IllegalArgumentException("Currency is required.");
        }

        if (!amount.getCurrency().equalsIgnoreCase(expectedCurrency)) {
            throw new IllegalArgumentException("Currency must be " + expectedCurrency + ".");
        }
    }
}