package com.teralume.energycore.notifications.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAlertRuleProfileResource(
        @NotBlank String name,
        String description,
        String scopeType,
        String scopeId,
        String mode,
        String sensitivity
) {
}
