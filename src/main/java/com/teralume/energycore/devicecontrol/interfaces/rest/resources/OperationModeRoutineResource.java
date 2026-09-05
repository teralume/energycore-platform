package com.teralume.energycore.devicecontrol.interfaces.rest.resources;

import com.teralume.energycore.devicecontrol.domain.model.entities.OperationModeRoutine;

public record OperationModeRoutineResource(
        String name,
        String targetType,
        Long targetId,
        String action,
        String triggerTime,
        Boolean enabled
) {
    public static OperationModeRoutineResource from(OperationModeRoutine routine) {
        return new OperationModeRoutineResource(
                routine.getName(),
                routine.getTargetType().name(),
                routine.getTargetId(),
                routine.getAction().name(),
                routine.getTriggerTime(),
                routine.getEnabled()
        );
    }
}
