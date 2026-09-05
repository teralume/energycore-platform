package com.teralume.energycore.energymonitoring.application.queryservices;

import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;
import com.teralume.energycore.energymonitoring.domain.model.queries.GetEnergyDashboardSummaryQuery;
import com.teralume.energycore.energymonitoring.domain.model.queries.GetEnergyReadingsByUserQuery;
import com.teralume.energycore.energymonitoring.domain.model.queries.GetEnergySamplingSettingsQuery;
import com.teralume.energycore.energymonitoring.interfaces.rest.resources.EnergyDashboardSummaryResource;

import java.util.List;

public interface EnergyMonitoringQueryService {
    List<EnergyReading> handle(GetEnergyReadingsByUserQuery query);
    EnergyDashboardSummaryResource handle(GetEnergyDashboardSummaryQuery query);
    Integer handle(GetEnergySamplingSettingsQuery query);
}
