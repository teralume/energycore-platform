package com.teralume.energycore.shared.domain.valueobjects;

import java.util.regex.Pattern;

public record PhoneNumber(String value) {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9\\s-]{7,20}$");

    public PhoneNumber {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be blank.");
        }
        value = value.trim();
        if (!PHONE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Phone number format is invalid.");
        }
    }
}