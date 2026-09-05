package com.teralume.energycore.devicecontrol.domain.model.commands;

public record DeleteRoutineCommand(
        Long userId,
        Long routineId
) {
}