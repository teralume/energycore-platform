package com.teralume.energycore.notifications.interfaces.rest.transform;

import com.teralume.energycore.notifications.domain.model.aggregates.AlertRuleProfile;
import com.teralume.energycore.notifications.interfaces.rest.resources.AlertRuleProfileResource;

public class AlertRuleProfileResourceFromEntityAssembler {
    public static AlertRuleProfileResource toResourceFromEntity(AlertRuleProfile profile) {
        return AlertRuleProfileResource.from(profile);
    }
}
