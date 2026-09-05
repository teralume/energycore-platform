package com.teralume.energycore.notifications.interfaces.rest.resources;

import com.teralume.energycore.notifications.domain.model.aggregates.Alert;

public record AlertResource(
        Long id,
        Long userId,
        String title,
        String message,
        String level,
        String sourceType,
        String sourceId,
        String sourceLabel,
        String eventType,
        String threadKey,
        String evidence,
        String explanation,
        String recommendedAction,
        Integer severityScore,
        Integer repeatCount,
        Boolean active,
        Boolean resolved,
        Boolean readStatus,
        String firstDetectedAt,
        String lastTriggeredAt,
        String dismissedUntil,
        String expiresAt,
        Boolean expired,
        Boolean silenced
) {
    public static AlertResource from(Alert alert) {
        return new AlertResource(
                alert.getId(),
                alert.getUserId(),
                alert.getTitle(),
                alert.getMessage(),
                alert.getLevel(),
                alert.getSourceType() == null ? null : alert.getSourceType().name(),
                alert.getSourceId(),
                alert.getSourceLabel(),
                alert.getEventType() == null ? null : alert.getEventType().name(),
                alert.getThreadKey(),
                alert.getEvidence(),
                alert.getExplanation(),
                alert.getRecommendedAction(),
                alert.getSeverityScore(),
                alert.getRepeatCount(),
                alert.getActive(),
                alert.getResolved(),
                alert.getReadStatus(),
                alert.getFirstDetectedAt() == null ? null : alert.getFirstDetectedAt().toString(),
                alert.getLastTriggeredAt() == null ? null : alert.getLastTriggeredAt().toString(),
                alert.getDismissedUntil() == null ? null : alert.getDismissedUntil().toString(),
                alert.getExpiresAt() == null ? null : alert.getExpiresAt().toString(),
                alert.isExpired(),
                alert.isSilenced()
        );
    }
}
