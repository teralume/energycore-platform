package com.teralume.energycore.energymonitoring.domain.specifications;

import com.teralume.energycore.energymonitoring.domain.model.valueobjects.EnergyThreshold;
import com.teralume.energycore.energymonitoring.domain.model.valueobjects.Watts;

import java.math.BigDecimal;

public class HighConsumptionSpecification {

    private final EnergyThreshold threshold;

    public HighConsumptionSpecification() {
        this(BigDecimal.valueOf(1800));
    }

    public HighConsumptionSpecification(BigDecimal thresholdWatts) {
        this.threshold = new EnergyThreshold(thresholdWatts);
    }

    public boolean isSatisfiedBy(BigDecimal watts) {
        return threshold.isExceededBy(new Watts(watts));
    }
}