package com.teralume.energycore.energymonitoring.domain.model.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.Money;

import java.math.BigDecimal;

public record EnergyCost(Money value) {
    public EnergyCost(BigDecimal amount) {
        this(Money.of(amount));
    }
}