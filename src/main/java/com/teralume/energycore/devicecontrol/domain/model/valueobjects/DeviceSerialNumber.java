package com.teralume.energycore.devicecontrol.domain.model.valueobjects;

import java.util.regex.Pattern;

public record DeviceSerialNumber(String value) {
    private static final Pattern PATTERN = Pattern.compile("^[A-Z0-9-]{6,40}$");

    public DeviceSerialNumber {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Device serial number cannot be blank.");
        }
        value = value.trim().toUpperCase();
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Device serial number format is invalid.");
        }
    }
}