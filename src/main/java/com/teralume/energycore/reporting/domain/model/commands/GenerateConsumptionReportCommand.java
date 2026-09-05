package com.teralume.energycore.reporting.domain.model.commands;

import java.time.LocalDate;

public record GenerateConsumptionReportCommand(
        Long userId,
        LocalDate startDate,
        LocalDate endDate
) {
}