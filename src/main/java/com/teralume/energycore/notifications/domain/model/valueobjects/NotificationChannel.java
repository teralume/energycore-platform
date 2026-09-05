package com.teralume.energycore.notifications.domain.model.valueobjects;

import java.util.Set;

public record NotificationChannel(String value) {
    private static final Set<String> ALLOWED = Set.of("EMAIL", "PUSH", "IN_APP");

    public NotificationChannel {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Notification channel is required.");
        }
        value = value.trim().toUpperCase();
        if (!ALLOWED.contains(value)) {
            throw new IllegalArgumentException("Notification channel is invalid.");
        }
    }
}