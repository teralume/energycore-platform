package com.teralume.energycore.billing.interfaces.rest.transform;

import com.teralume.energycore.billing.domain.model.aggregates.Plan;
import com.teralume.energycore.billing.interfaces.rest.resources.PlanResource;

public class PlanResourceFromEntityAssembler {
    private PlanResourceFromEntityAssembler() {
    }

    public static PlanResource toResourceFromEntity(Plan entity) {
        return PlanResource.from(entity);
    }
}