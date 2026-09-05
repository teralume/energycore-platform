package com.teralume.energycore.notifications.domain.services;

import com.teralume.energycore.notifications.domain.model.valueobjects.AlertEventType;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertLevel;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertSourceType;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AlertPolicyService {

    private static final Set<String> ALLOWED_LEVELS = Set.of(
            "STABLE",
            "INFO",
            "WARNING",
            "CRITICAL",
            "SUCCESS"
    );

    public void validateAlert(String title, String message, String level) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Alert title is required.");
        }

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Alert message is required.");
        }

        if (!isValidLevel(level)) {
            throw new IllegalArgumentException("Alert level is invalid.");
        }
    }

    public boolean isValidLevel(String level) {
        return level != null && ALLOWED_LEVELS.contains(level.toUpperCase());
    }

    public boolean isCritical(String level) {
        return "CRITICAL".equalsIgnoreCase(level);
    }

    public AlertLevel parseLevel(String level) {
        if (!isValidLevel(level)) {
            throw new IllegalArgumentException("Alert level is invalid.");
        }

        return AlertLevel.valueOf(level.trim().toUpperCase());
    }

    public AlertSourceType parseSourceType(String sourceType) {
        if (sourceType == null || sourceType.isBlank()) {
            return AlertSourceType.SYSTEM;
        }

        try {
            return AlertSourceType.valueOf(sourceType.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Alert source type is invalid.");
        }
    }

    public AlertEventType parseEventType(String eventType) {
        if (eventType == null || eventType.isBlank()) {
            return AlertEventType.MANUAL;
        }

        try {
            return AlertEventType.valueOf(eventType.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Alert event type is invalid.");
        }
    }

    public int normalizeScore(Integer score) {
        if (score == null) {
            return 25;
        }

        return Math.max(0, Math.min(100, score));
    }
}
