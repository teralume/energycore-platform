package com.teralume.energycore.devicecontrol.domain.model.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.NonBlankText;

public record RoutineName(String value) {
    public RoutineName {
        value = NonBlankText.of(value).value();
        if (value.length() > 120) {
            throw new IllegalArgumentException("Routine name cannot exceed 120 characters.");
        }
    }
}