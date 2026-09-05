package com.teralume.energycore.reporting.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateEnergyGoalResource(
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
