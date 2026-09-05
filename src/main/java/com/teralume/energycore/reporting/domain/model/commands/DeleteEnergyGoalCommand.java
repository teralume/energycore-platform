package com.teralume.energycore.reporting.domain.model.commands;

public record DeleteEnergyGoalCommand(
        Long userId,
        Long goalId
) {
}