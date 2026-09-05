package com.teralume.energycore.energymonitoring.domain.model.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record KilowattHours(BigDecimal value) {
    public KilowattHours {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Kilowatt hours cannot be negative.");
        }
        value = value.setScale(6, RoundingMode.HALF_UP);
    }
}