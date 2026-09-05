package com.teralume.energycore.devicecontrol.domain.model.commands;

public record ToggleRoutineCommand(
        Long userId,
        Long routineId
) {
}