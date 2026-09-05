package com.teralume.energycore.notifications.domain.model.results;

import com.teralume.energycore.notifications.domain.model.valueobjects.AlertEventType;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertLevel;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertSourceType;
import com.teralume.energycore.notifications.domain.model.valueobjects.RuleScopeType;

public record RuleEvaluationResult(
        Long userId,
        RuleScopeType scopeType,
        String scopeId,
        AlertLevel level,
        Integer severityScore,
        String evidence,
        String explanation,
        String recommendedAction,
        AlertSourceType sourceType,
        String sourceId,
        AlertEventType eventType,
        String threadKey,
        Integer activeEvaluatorCount,
        Integer totalWeight
) {
}
