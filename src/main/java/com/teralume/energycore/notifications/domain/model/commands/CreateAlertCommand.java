package com.teralume.energycore.notifications.domain.model.commands;

public record CreateAlertCommand(
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
        String expiresAt
) {
}
