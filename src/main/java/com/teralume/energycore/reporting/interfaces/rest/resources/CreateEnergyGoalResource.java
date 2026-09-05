package com.teralume.energycore.reporting.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record CreateEnergyGoalResource(
        @NotBlank String title,
        BigDecimal targetKilowattHours,
        BigDecimal targetWatts,
        @NotNull LocalDate deadline,
        String scopeType,
        Long scopeId,
        String scopeName,
        LocalTime activeFrom,
        LocalTime activeTo
) {
    public BigDecimal effectiveTargetKilowattHours() {
        return targetKilowattHours != null ? targetKilowattHours : targetWatts;
    }
}
