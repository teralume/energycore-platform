package com.teralume.energycore.notifications.domain.model.aggregates;

import com.teralume.energycore.notifications.domain.model.valueobjects.AlertLevel;
import com.teralume.energycore.notifications.domain.model.valueobjects.RuleEvaluatorType;
import com.teralume.energycore.notifications.domain.model.valueobjects.RuleScopeType;
import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alert_rules")
public class AlertRule extends AuditableEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 60)
    private String metric;

    @Column(nullable = false, length = 40)
    private String conditionType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal threshold;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AlertLevel level = AlertLevel.WARNING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private RuleScopeType scopeType = RuleScopeType.GENERAL;

    @Column(length = 80)
    private String scopeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private RuleEvaluatorType evaluatorType = RuleEvaluatorType.ACTIVE_POWER;

    @Column(nullable = false)
    private Integer weight = 10;

    @Column(nullable = false, length = 120)
    private String profileName = "General";

    @Column(nullable = false)
    private Boolean enabled = true;

    public void toggle() {
        this.enabled = !this.enabled;
    }

    public boolean appliesTo(RuleScopeType requestedScopeType, String requestedScopeId) {
        if (this.scopeType == RuleScopeType.GENERAL) {
            return true;
        }

        if (requestedScopeType == null || this.scopeType != requestedScopeType) {
            return false;
        }

        if (this.scopeId == null || this.scopeId.isBlank()) {
            return true;
        }

        return this.scopeId.equals(requestedScopeId);
    }

    public int effectiveWeight() {
        return Math.max(0, this.weight == null ? 0 : this.weight);
    }
}
