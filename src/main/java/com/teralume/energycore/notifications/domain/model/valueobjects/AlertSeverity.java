package com.teralume.energycore.notifications.domain.model.valueobjects;

import java.util.Set;

public record AlertSeverity(String value) {
    private static final Set<String> ALLOWED = Set.of(
            "STABLE",
            "INFO",
            "WARNING",
            "CRITICAL",
            "SUCCESS"
    );

    public AlertSeverity {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Alert severity is required.");
        }
        value = value.trim().toUpperCase();
        if (!ALLOWED.contains(value)) {
            throw new IllegalArgumentException("Alert severity is invalid.");
        }
    }
}
