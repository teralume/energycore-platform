package com.teralume.energycore.reporting.domain.services;

import com.teralume.energycore.reporting.domain.model.aggregates.ConsumptionReport;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReportSummaryService {

    public BigDecimal calculateTotalReportedWatts(List<ConsumptionReport> reports) {
        if (reports == null || reports.isEmpty()) return BigDecimal.ZERO;

        return reports.stream()
                .map(ConsumptionReport::getTotalWatts)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}