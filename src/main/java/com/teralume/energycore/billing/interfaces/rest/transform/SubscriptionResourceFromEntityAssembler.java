package com.teralume.energycore.billing.interfaces.rest.transform;

import com.teralume.energycore.billing.domain.model.aggregates.Subscription;
import com.teralume.energycore.billing.interfaces.rest.resources.SubscriptionResource;

public class SubscriptionResourceFromEntityAssembler {
    private SubscriptionResourceFromEntityAssembler() {
    }

    public static SubscriptionResource toResourceFromEntity(Subscription entity) {
        return entity == null ? null : SubscriptionResource.from(entity);
    }
}