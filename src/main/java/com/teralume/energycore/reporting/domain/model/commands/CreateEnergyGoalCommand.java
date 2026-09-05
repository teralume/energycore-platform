package com.teralume.energycore.reporting.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record CreateEnergyGoalCommand(
        Long userId,
        String title,
        BigDecimal targetKilowattHours,
        LocalDate deadline,
        String scopeType,
        Long scopeId,
        String scopeName,
        LocalTime activeFrom,
        LocalTime activeTo
) {
}
