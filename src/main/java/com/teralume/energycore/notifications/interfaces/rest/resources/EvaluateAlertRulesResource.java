package com.teralume.energycore.notifications.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record EvaluateAlertRulesResource(
        String scopeType,
        String scopeId,
        BigDecimal observedValue
) {
}
