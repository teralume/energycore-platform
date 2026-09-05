package com.teralume.energycore.reporting.interfaces.rest.transform;

import com.teralume.energycore.reporting.domain.model.aggregates.EnergyGoal;
import com.teralume.energycore.reporting.interfaces.rest.resources.EnergyGoalResource;

public class EnergyGoalResourceFromEntityAssembler {

    private EnergyGoalResourceFromEntityAssembler() {
    }

    public static EnergyGoalResource toResourceFromEntity(EnergyGoal entity) {
        return EnergyGoalResource.from(entity);
    }
}