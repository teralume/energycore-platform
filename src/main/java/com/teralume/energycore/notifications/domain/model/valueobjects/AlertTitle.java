package com.teralume.energycore.notifications.domain.model.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.NonBlankText;

public record AlertTitle(String value) {
    public AlertTitle {
        value = NonBlankText.of(value).value();
        if (value.length() > 120) {
            throw new IllegalArgumentException("Alert title cannot exceed 120 characters.");
        }
    }
}