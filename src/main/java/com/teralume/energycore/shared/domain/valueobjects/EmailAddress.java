package com.teralume.energycore.shared.domain.valueobjects;

import java.util.regex.Pattern;

public record EmailAddress(String value) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    public EmailAddress {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Email format is invalid.");
        }

        value = value.toLowerCase();
    }
}