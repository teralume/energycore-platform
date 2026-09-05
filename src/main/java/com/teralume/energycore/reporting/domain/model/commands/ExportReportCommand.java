package com.teralume.energycore.reporting.domain.model.commands;

import java.time.LocalDate;

public record ExportReportCommand(
        Long userId,
        LocalDate startDate,
        LocalDate endDate,
        String format
) {
}