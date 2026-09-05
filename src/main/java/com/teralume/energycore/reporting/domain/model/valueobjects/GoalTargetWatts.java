package com.teralume.energycore.reporting.domain.model.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record GoalTargetWatts(BigDecimal value) {
    public GoalTargetWatts {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Goal target watts must be greater than zero.");
        }
        value = value.setScale(2, RoundingMode.HALF_UP);
    }
}