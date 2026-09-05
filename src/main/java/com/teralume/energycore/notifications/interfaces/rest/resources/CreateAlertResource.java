package com.teralume.energycore.notifications.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAlertResource(
        @NotBlank String title,
        @NotBlank String message,
        @NotBlank String level,
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
