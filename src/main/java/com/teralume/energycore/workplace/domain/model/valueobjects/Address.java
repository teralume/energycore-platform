package com.teralume.energycore.workplace.domain.model.valueobjects;

public record Address(String value) {
    public Address {
        if (value == null) {
            value = "";
        }
        value = value.trim();
        if (value.length() > 255) {
            throw new IllegalArgumentException("Address cannot exceed 255 characters.");
        }
    }
}