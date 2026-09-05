package com.teralume.energycore.notifications.interfaces.rest.resources;

import com.teralume.energycore.notifications.domain.model.results.RuleEvaluationResult;

public record RuleEvaluationResultResource(
        Long userId,
        String scopeType,
        String scopeId,
        String level,
        Integer severityScore,
        String evidence,
        String explanation,
        String recommendedAction,
        String sourceType,
        String sourceId,
        String eventType,
        String threadKey,
        Integer activeEvaluatorCount,
        Integer totalWeight
) {
    public static RuleEvaluationResultResource from(RuleEvaluationResult result) {
        return new RuleEvaluationResultResource(
                result.userId(),
                result.scopeType().name(),
                result.scopeId(),
                result.level().name(),
                result.severityScore(),
                result.evidence(),
                result.explanation(),
                result.recommendedAction(),
                result.sourceType().name(),
                result.sourceId(),
                result.eventType().name(),
                result.threadKey(),
                result.activeEvaluatorCount(),
                result.totalWeight()
        );
    }
}
