package com.teralume.energycore.reporting.interfaces.rest.transform;

import com.teralume.energycore.reporting.domain.model.commands.UpdateEnergyGoalCommand;
import com.teralume.energycore.reporting.interfaces.rest.resources.UpdateEnergyGoalResource;

public class UpdateEnergyGoalCommandFromResourceAssembler {
    public static UpdateEnergyGoalCommand toCommandFromResource(
            UpdateEnergyGoalResource resource,
            Long userId,
            Long goalId
    ) {
        return new UpdateEnergyGoalCommand(
                userId,
                goalId,
                resource.title(),
                resource.targetKilowattHours(),
                resource.currentKilowattHours(),
                resource.deadline(),
                resource.status(),
                resource.scopeType(),
                resource.scopeId(),
                resource.scopeName(),
                resource.activeFrom(),
                resource.activeTo()
        );
    }
}
