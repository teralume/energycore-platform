package com.teralume.energycore.servicemanagement.domain.model.valueobjects;

import java.util.Set;

public record Priority(String value) {
    private static final Set<String> ALLOWED = Set.of("LOW", "MEDIUM", "HIGH", "CRITICAL");

    public Priority {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Priority is required.");
        }
        value = value.trim().toUpperCase();
        if (!ALLOWED.contains(value)) {
            throw new IllegalArgumentException("Priority is invalid.");
        }
    }
}