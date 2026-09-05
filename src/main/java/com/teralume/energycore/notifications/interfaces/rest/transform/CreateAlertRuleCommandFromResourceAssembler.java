package com.teralume.energycore.notifications.interfaces.rest.transform;

import com.teralume.energycore.notifications.domain.model.commands.CreateAlertRuleCommand;
import com.teralume.energycore.notifications.interfaces.rest.resources.CreateAlertRuleResource;

public class CreateAlertRuleCommandFromResourceAssembler {
    public static CreateAlertRuleCommand toCommandFromResource(CreateAlertRuleResource resource, Long userId) {
        return new CreateAlertRuleCommand(
                userId,
                resource.name(),
                resource.metric(),
                resource.conditionType(),
                resource.threshold(),
                resource.level(),
                resource.scopeType(),
                resource.scopeId(),
                resource.evaluatorType(),
                resource.weight(),
                resource.profileName()
        );
    }
}
