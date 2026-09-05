package com.teralume.energycore.notifications.domain.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationPreferencePolicyService {

    private static final Set<String> ALLOWED_LEVELS = Set.of(
            "STABLE",
            "INFO",
            "WARNING",
            "CRITICAL",
            "SUCCESS"
    );

    private static final Set<String> ALLOWED_SOURCES = Set.of(
            "DEVICE",
            "GROUP",
            "ROOM",
            "WORKPLACE",
            "ROUTINE",
            "GOAL",
            "REPORT",
            "RULE",
            "MODE",
            "SYSTEM"
    );

    public void validatePreference(String minimumLevel) {
        if (minimumLevel == null || minimumLevel.isBlank()) {
            throw new IllegalArgumentException("Minimum notification level is required.");
        }

        if (!ALLOWED_LEVELS.contains(minimumLevel.toUpperCase())) {
            throw new IllegalArgumentException("Minimum notification level is invalid.");
        }
    }

    public String normalizeLevelSet(String levels) {
        return normalizeSet(levels, ALLOWED_LEVELS, "Allowed notification levels are invalid.");
    }

    public String normalizeSourceSet(String sources) {
        return normalizeSet(sources, ALLOWED_SOURCES, "Allowed notification sources are invalid.");
    }

    public boolean allowsLevel(String configuredMinimumLevel, String alertLevel) {
        int configuredPriority = priorityOf(configuredMinimumLevel);
        int alertPriority = priorityOf(alertLevel);

        return alertPriority >= configuredPriority;
    }

    private int priorityOf(String level) {
        if (level == null) return 0;

        return switch (level.toUpperCase()) {
            case "STABLE" -> 1;
            case "SUCCESS" -> 2;
            case "INFO" -> 3;
            case "WARNING" -> 4;
            case "CRITICAL" -> 5;
            default -> 0;
        };
    }

    private String normalizeSet(String value, Set<String> allowedValues, String errorMessage) {
        if (value == null || value.isBlank()) {
            return null;
        }

        Set<String> normalized = Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(item -> !item.isBlank())
                .map(item -> item.toUpperCase(Locale.ROOT))
                .collect(Collectors.toCollection(java.util.LinkedHashSet::new));

        if (normalized.isEmpty() || !allowedValues.containsAll(normalized)) {
            throw new IllegalArgumentException(errorMessage);
        }

        return String.join(",", normalized);
    }
}
