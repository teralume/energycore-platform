package com.teralume.energycore.reporting.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateEnergyGoalCommand(
        Long userId,
        Long goalId,
        String title,
        BigDecimal targetKilowattHours,
        BigDecimal currentKilowattHours,
        LocalDate deadline,
        String status,
        String scopeType,
        Long scopeId,
        String scopeName,
        LocalTime activeFrom,
        LocalTime activeTo
) {
}
