package com.teralume.energycore.shared.domain.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Percentage(BigDecimal value) {
    public Percentage {
        if (value == null) {
            throw new IllegalArgumentException("Percentage is required.");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100.");
        }
        value = value.setScale(2, RoundingMode.HALF_UP);
    }

    public static Percentage of(BigDecimal value) {
        return new Percentage(value);
    }
}