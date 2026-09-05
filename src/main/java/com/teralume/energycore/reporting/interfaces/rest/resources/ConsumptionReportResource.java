package com.teralume.energycore.reporting.interfaces.rest.resources;

import com.teralume.energycore.reporting.domain.model.aggregates.ConsumptionReport;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ConsumptionReportResource(
        Long id,
        Long userId,
        BigDecimal totalWatts,
        BigDecimal averageWatts,
        BigDecimal highestWatts,
        LocalDate startDate,
        LocalDate endDate
) {
    public static ConsumptionReportResource from(ConsumptionReport report) {
        return new ConsumptionReportResource(
                report.getId(),
                report.getUserId(),
                report.getTotalWatts(),
                report.getAverageWatts(),
                report.getHighestWatts(),
                report.getStartDate(),
                report.getEndDate()
        );
    }
}