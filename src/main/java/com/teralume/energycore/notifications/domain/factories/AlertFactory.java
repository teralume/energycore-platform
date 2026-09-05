package com.teralume.energycore.notifications.domain.factories;

import com.teralume.energycore.notifications.domain.model.aggregates.Alert;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertEventType;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertMessage;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertSourceType;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertSeverity;
import com.teralume.energycore.shared.domain.valueobjects.NonBlankText;

import java.time.LocalDateTime;

public class AlertFactory {
    public Alert create(
            Long userId,
            String title,
            String message,
            String severity,
            AlertSourceType sourceType,
            String sourceId,
            String sourceLabel,
            AlertEventType eventType,
            String threadKey,
            String evidence,
            String explanation,
            String recommendedAction,
            Integer severityScore,
            LocalDateTime expiresAt
    ) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id must be positive.");
        }

        LocalDateTime now = LocalDateTime.now();
        Alert alert = new Alert();
        alert.setUserId(userId);
        alert.setTitle(NonBlankText.of(title).value());
        alert.setMessage(new AlertMessage(message).value());
        alert.setLevel(new AlertSeverity(severity).value());
        alert.setSourceType(sourceType == null ? AlertSourceType.SYSTEM : sourceType);
        alert.setSourceId(normalizeOptional(sourceId));
        alert.setSourceLabel(normalizeOptional(sourceLabel));
        alert.setEventType(eventType == null ? AlertEventType.MANUAL : eventType);
        alert.setThreadKey(NonBlankText.of(threadKey).value());
        alert.setEvidence(normalizeOptional(evidence));
        alert.setExplanation(normalizeOptional(explanation));
        alert.setRecommendedAction(normalizeOptional(recommendedAction));
        alert.setSeverityScore(severityScore);
        alert.setFirstDetectedAt(now);
        alert.setLastTriggeredAt(now);
        alert.setExpiresAt(expiresAt);
        alert.setRepeatCount(1);
        alert.setActive(true);
        alert.setResolved(false);
        alert.setReadStatus(false);
        return alert;
    }

    private String normalizeOptional(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
