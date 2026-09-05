package com.teralume.energycore.reporting.domain.model.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.NonBlankText;

public record EnergyGoalTitle(String value) {
    public EnergyGoalTitle {
        value = NonBlankText.of(value).value();
        if (value.length() > 120) {
            throw new IllegalArgumentException("Energy goal title cannot exceed 120 characters.");
        }
    }
}