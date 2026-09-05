package com.teralume.energycore.billing.domain.model.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.NonBlankText;

public record PlanName(String value) {
    public PlanName {
        value = NonBlankText.of(value).value();
        if (value.length() > 80) {
            throw new IllegalArgumentException("Plan name cannot exceed 80 characters.");
        }
    }
}