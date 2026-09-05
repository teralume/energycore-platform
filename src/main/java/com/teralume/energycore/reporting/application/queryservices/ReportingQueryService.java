package com.teralume.energycore.reporting.application.queryservices;

import com.teralume.energycore.reporting.domain.model.aggregates.ConsumptionReport;
import com.teralume.energycore.reporting.domain.model.aggregates.EnergyGoal;
import com.teralume.energycore.reporting.domain.model.aggregates.ReportingEvent;
import com.teralume.energycore.reporting.domain.model.queries.GetConsumptionReportsByUserQuery;
import com.teralume.energycore.reporting.domain.model.queries.GetEnergyGoalsByUserQuery;
import com.teralume.energycore.reporting.domain.model.queries.GetReportingEventsByUserQuery;

import java.util.List;

public interface ReportingQueryService {
    List<ConsumptionReport> handle(GetConsumptionReportsByUserQuery query);
    List<EnergyGoal> handle(GetEnergyGoalsByUserQuery query);
    List<ReportingEvent> handle(GetReportingEventsByUserQuery query);
}
