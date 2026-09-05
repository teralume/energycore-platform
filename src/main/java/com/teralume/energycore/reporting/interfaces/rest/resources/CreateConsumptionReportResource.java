package com.teralume.energycore.reporting.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateConsumptionReportResource(
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate
) {
}
