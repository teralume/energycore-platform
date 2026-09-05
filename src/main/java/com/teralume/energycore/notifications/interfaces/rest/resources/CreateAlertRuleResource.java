package com.teralume.energycore.notifications.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateAlertRuleResource(
        @NotBlank String name,
        @NotBlank String metric,
        @NotBlank String conditionType,
        @NotNull BigDecimal threshold,
        String level,
        String scopeType,
        String scopeId,
        String evaluatorType,
        Integer weight,
        String profileName
) {
}
