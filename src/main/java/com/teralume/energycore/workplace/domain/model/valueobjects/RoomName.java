package com.teralume.energycore.workplace.domain.model.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.NonBlankText;

public record RoomName(String value) {
    public RoomName {
        value = NonBlankText.of(value).value();
        if (value.length() > 120) {
            throw new IllegalArgumentException("Room name cannot exceed 120 characters.");
        }
    }
}