package com.teralume.energycore.iam.domain.model.valueobjects;

import java.util.regex.Pattern;

public record RoleName(String value) {
    private static final Pattern PATTERN = Pattern.compile("^[A-Z_]{3,40}$");

    public RoleName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Role name cannot be blank.");
        }
        value = value.trim().toUpperCase();
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Role name format is invalid.");
        }
    }
}