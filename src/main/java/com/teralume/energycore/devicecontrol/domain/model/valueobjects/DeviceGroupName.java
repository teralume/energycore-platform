package com.teralume.energycore.devicecontrol.domain.model.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.NonBlankText;

public record DeviceGroupName(String value) {
    public DeviceGroupName {
        value = NonBlankText.of(value).value();
        if (value.length() > 120) {
            throw new IllegalArgumentException("Device group name cannot exceed 120 characters.");
        }
    }
}