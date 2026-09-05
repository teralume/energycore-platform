package com.teralume.energycore.notifications.domain.services;

import com.teralume.energycore.notifications.domain.model.aggregates.AlertRule;
import com.teralume.energycore.notifications.domain.model.results.RuleEvaluationResult;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertEventType;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertLevel;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertSourceType;
import com.teralume.energycore.notifications.domain.model.valueobjects.RuleScopeType;
import com.teralume.energycore.notifications.domain.repositories.AlertRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleEvaluationService {

    private final AlertRuleRepository alertRuleRepository;
    private final RuleSeverityPolicyService severityPolicyService;

    public RuleEvaluationResult evaluate(Long userId, RuleScopeType scopeType, String scopeId, BigDecimal observedValue) {
        BigDecimal safeObservedValue = observedValue == null ? BigDecimal.ZERO : observedValue;
        List<AlertRule> activeRules = alertRuleRepository.findByUserIdAndEnabledTrue(userId).stream()
                .filter(rule -> rule.appliesTo(scopeType, scopeId))
                .toList();

        int totalWeight = activeRules.stream().mapToInt(AlertRule::effectiveWeight).sum();
        int score = totalWeight <= 0
                ? 0
                : activeRules.stream()
                .mapToInt(rule -> contribution(rule, safeObservedValue, totalWeight))
                .sum();
        AlertLevel level = severityPolicyService.classify(score);
        String evidence = buildEvidence(activeRules, safeObservedValue, totalWeight);

        return new RuleEvaluationResult(
                userId,
                scopeType,
                scopeId,
                level,
                score,
                evidence,
                "El motor evaluo %d reglas activas con peso total %d para el scope %s.".formatted(
                        activeRules.size(),
                        totalWeight,
                        scopeType.name()
                ),
                severityPolicyService.recommendedAction(level),
                sourceTypeFromScope(scopeType),
                scopeId,
                AlertEventType.RULE_EVALUATION,
                "RULE:%s:%s".formatted(scopeType.name(), scopeId == null || scopeId.isBlank() ? "GENERAL" : scopeId),
                activeRules.size(),
                totalWeight
        );
    }

    private int contribution(AlertRule rule, BigDecimal observedValue, int totalWeight) {
        if (totalWeight <= 0 || rule.effectiveWeight() <= 0) {
            return 0;
        }

        BigDecimal threshold = rule.getThreshold() == null || rule.getThreshold().compareTo(BigDecimal.ZERO) <= 0
                ? BigDecimal.ONE
                : rule.getThreshold();
        BigDecimal ratio = observedValue.divide(threshold, 6, RoundingMode.HALF_UP);
        boolean conditionMatched = switch (rule.getConditionType()) {
            case "GREATER_OR_EQUAL_THAN" -> observedValue.compareTo(threshold) >= 0;
            case "GREATER_THAN" -> observedValue.compareTo(threshold) > 0;
            default -> false;
        };
        BigDecimal boundedRatio = ratio.min(BigDecimal.valueOf(1.5));
        BigDecimal weighted = BigDecimal.valueOf(rule.effectiveWeight())
                .divide(BigDecimal.valueOf(totalWeight), 6, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .multiply(conditionMatched ? boundedRatio : boundedRatio.multiply(BigDecimal.valueOf(0.35)));

        return Math.max(0, Math.min(100, weighted.setScale(0, RoundingMode.HALF_UP).intValue()));
    }

    private String buildEvidence(List<AlertRule> rules, BigDecimal observedValue, int totalWeight) {
        if (rules.isEmpty()) {
            return "No hay evaluadores activos para este scope.";
        }

        String dominant = rules.stream()
                .max((first, second) -> Integer.compare(first.effectiveWeight(), second.effectiveWeight()))
                .map(AlertRule::getName)
                .orElse("Sin evaluador dominante");

        return "Valor observado: %s. Evaluadores: %d. Peso activo: %d. Dominante: %s.".formatted(
                observedValue.stripTrailingZeros().toPlainString(),
                rules.size(),
                totalWeight,
                dominant
        );
    }

    private AlertSourceType sourceTypeFromScope(RuleScopeType scopeType) {
        return switch (scopeType) {
            case DEVICE -> AlertSourceType.DEVICE;
            case GROUP -> AlertSourceType.GROUP;
            case ROOM -> AlertSourceType.ROOM;
            case WORKPLACE -> AlertSourceType.WORKPLACE;
            case ROUTINE -> AlertSourceType.ROUTINE;
            case GOAL -> AlertSourceType.GOAL;
            case GENERAL -> AlertSourceType.RULE;
        };
    }
}
