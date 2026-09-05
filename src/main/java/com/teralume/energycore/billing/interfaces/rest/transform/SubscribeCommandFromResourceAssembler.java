package com.teralume.energycore.billing.interfaces.rest.transform;

import com.teralume.energycore.billing.domain.model.commands.SubscribeCommand;
import com.teralume.energycore.billing.interfaces.rest.resources.SubscribeResource;

public class SubscribeCommandFromResourceAssembler {
    private SubscribeCommandFromResourceAssembler() {
    }

    public static SubscribeCommand toCommandFromResource(SubscribeResource resource, Long userId) {
        return new SubscribeCommand(userId, resource.planCode());
    }
}
