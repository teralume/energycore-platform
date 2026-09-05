package com.teralume.energycore.notifications.interfaces.rest.resources;

import com.teralume.energycore.notifications.domain.model.aggregates.AlertRule;

import java.math.BigDecimal;

public record AlertRuleResource(
        Long id,
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
        String profileName,
        Boolean enabled
) {
    public static AlertRuleResource from(AlertRule rule) {
        return new AlertRuleResource(
                rule.getId(),
                rule.getUserId(),
                rule.getName(),
                rule.getMetric(),
                rule.getConditionType(),
                rule.getThreshold(),
                rule.getLevel() == null ? null : rule.getLevel().name(),
                rule.getScopeType() == null ? null : rule.getScopeType().name(),
                rule.getScopeId(),
                rule.getEvaluatorType() == null ? null : rule.getEvaluatorType().name(),
                rule.getWeight(),
                rule.getProfileName(),
                rule.getEnabled()
        );
    }
}
