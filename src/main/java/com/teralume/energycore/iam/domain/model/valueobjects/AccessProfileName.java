package com.teralume.energycore.iam.domain.model.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.NonBlankText;

public record AccessProfileName(String value) {
    public AccessProfileName {
        value = NonBlankText.of(value).value();
        if (value.length() > 80) {
            throw new IllegalArgumentException("Access profile name cannot exceed 80 characters.");
        }
    }
}