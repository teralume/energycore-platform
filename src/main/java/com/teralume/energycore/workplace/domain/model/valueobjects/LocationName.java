package com.teralume.energycore.workplace.domain.model.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.NonBlankText;

public record LocationName(String value) {
    public LocationName {
        value = NonBlankText.of(value).value();
        if (value.length() > 120) {
            throw new IllegalArgumentException("Location name cannot exceed 120 characters.");
        }
    }
}