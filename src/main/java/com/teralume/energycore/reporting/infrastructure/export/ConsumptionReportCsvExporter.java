package com.teralume.energycore.reporting.infrastructure.export;

import com.teralume.energycore.reporting.domain.model.aggregates.ConsumptionReport;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class ConsumptionReportCsvExporter {

    public byte[] export(List<ConsumptionReport> reports) {
        StringBuilder csv = new StringBuilder();

        csv.append("id,userId,totalWatts,averageWatts,highestWatts,startDate,endDate")
                .append(System.lineSeparator());

        if (reports != null) {
            for (ConsumptionReport report : reports) {
                csv.append(report.getId()).append(",")
                        .append(report.getUserId()).append(",")
                        .append(report.getTotalWatts()).append(",")
                        .append(report.getAverageWatts()).append(",")
                        .append(report.getHighestWatts()).append(",")
                        .append(report.getStartDate()).append(",")
                        .append(report.getEndDate())
                        .append(System.lineSeparator());
            }
        }

        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }
}