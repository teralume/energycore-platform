package com.teralume.energycore.notifications.interfaces.rest.transform;

import com.teralume.energycore.notifications.domain.model.aggregates.AlertRule;
import com.teralume.energycore.notifications.interfaces.rest.resources.AlertRuleResource;

public class AlertRuleResourceFromEntityAssembler {
    public static AlertRuleResource toResourceFromEntity(AlertRule rule) {
        return AlertRuleResource.from(rule);
    }
}
