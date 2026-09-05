package com.teralume.energycore.energymonitoring.domain.model.valueobjects;

import java.math.BigDecimal;

public record EnergyThreshold(Watts watts) {
    public EnergyThreshold(BigDecimal value) {
        this(new Watts(value));
    }

    public boolean isExceededBy(Watts reading) {
        return reading != null && reading.value().compareTo(watts.value()) >= 0;
    }
}