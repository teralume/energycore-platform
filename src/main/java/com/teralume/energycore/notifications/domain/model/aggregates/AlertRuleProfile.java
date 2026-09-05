package com.teralume.energycore.notifications.domain.model.aggregates;

import com.teralume.energycore.notifications.domain.model.valueobjects.RuleProfileMode;
import com.teralume.energycore.notifications.domain.model.valueobjects.RuleScopeType;
import com.teralume.energycore.notifications.domain.model.valueobjects.RuleSensitivity;
import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alert_rule_profiles")
public class AlertRuleProfile extends AuditableEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 240)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RuleScopeType scopeType = RuleScopeType.GENERAL;

    @Column(length = 80)
    private String scopeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RuleProfileMode mode = RuleProfileMode.BALANCED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RuleSensitivity sensitivity = RuleSensitivity.NORMAL;

    @Column(nullable = false)
    private Boolean active = false;

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
