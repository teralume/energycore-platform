package com.teralume.energycore.devicecontrol.domain.model.commands;

import com.teralume.energycore.devicecontrol.domain.model.RoutineAction;
import com.teralume.energycore.devicecontrol.domain.model.RoutineTargetType;

public record OperationModeRoutineCommand(
        String name,
        RoutineTargetType targetType,
        Long targetId,
        RoutineAction action,
        String triggerTime,
        Boolean enabled
) {
}
