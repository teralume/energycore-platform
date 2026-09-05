package com.teralume.energycore.devicecontrol.domain.model.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record PowerWatts(BigDecimal value) {
    public PowerWatts {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Power watts must be greater than zero.");
        }
        if (value.compareTo(BigDecimal.valueOf(10000)) > 0) {
            throw new IllegalArgumentException("Power watts exceeds the supported range.");
        }
        value = value.setScale(2, RoundingMode.HALF_UP);
    }
}