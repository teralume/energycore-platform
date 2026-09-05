package com.teralume.energycore.devicecontrol.domain.model.commands;

public record ExecuteRoutineCommand(
        Long userId,
        Long routineId
) {
}
