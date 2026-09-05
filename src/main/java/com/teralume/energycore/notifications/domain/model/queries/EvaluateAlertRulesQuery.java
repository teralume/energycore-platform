package com.teralume.energycore.notifications.domain.model.queries;

import java.math.BigDecimal;

public record EvaluateAlertRulesQuery(
        Long userId,
        String scopeType,
        String scopeId,
        BigDecimal observedValue
) {
}
