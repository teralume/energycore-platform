package com.teralume.energycore.notifications.domain.model.commands;

import java.math.BigDecimal;

public record CreateAlertRuleCommand(
        Long userId,
        String name,
        String metric,
        String conditionType,
        BigDecimal threshold,
        String level,
        String scopeType,
        String scopeId,
        String evaluatorType,
        Integer weight,
        String profileName
) {
}
