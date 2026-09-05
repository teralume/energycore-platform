package com.teralume.energycore.reporting.application.internal.queryservices;

import com.teralume.energycore.reporting.application.queryservices.ReportingQueryService;
import com.teralume.energycore.reporting.application.services.ReportingApplicationService;
import com.teralume.energycore.reporting.domain.model.aggregates.ConsumptionReport;
import com.teralume.energycore.reporting.domain.model.aggregates.EnergyGoal;
import com.teralume.energycore.reporting.domain.model.aggregates.ReportingEvent;
import com.teralume.energycore.reporting.domain.model.queries.GetConsumptionReportsByUserQuery;
import com.teralume.energycore.reporting.domain.model.queries.GetEnergyGoalsByUserQuery;
import com.teralume.energycore.reporting.domain.model.queries.GetReportingEventsByUserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportingQueryServiceImpl implements ReportingQueryService {
    private final ReportingApplicationService applicationService;

    @Override
    public List<ConsumptionReport> handle(GetConsumptionReportsByUserQuery query) {
        return applicationService.getReports(query.userId());
    }

    @Override
    public List<EnergyGoal> handle(GetEnergyGoalsByUserQuery query) {
        return applicationService.getGoals(query.userId());
    }

    @Override
    public List<ReportingEvent> handle(GetReportingEventsByUserQuery query) {
        return applicationService.getEvents(query.userId());
    }
}
