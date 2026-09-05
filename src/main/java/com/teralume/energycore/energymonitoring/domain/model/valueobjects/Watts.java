package com.teralume.energycore.energymonitoring.domain.model.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Watts(BigDecimal value) {
    public Watts {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Watts cannot be negative.");
        }
        value = value.setScale(2, RoundingMode.HALF_UP);
    }
}