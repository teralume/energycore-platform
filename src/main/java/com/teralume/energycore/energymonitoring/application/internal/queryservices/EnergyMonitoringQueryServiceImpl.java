package com.teralume.energycore.energymonitoring.application.internal.queryservices;

import com.teralume.energycore.energymonitoring.application.queryservices.EnergyMonitoringQueryService;
import com.teralume.energycore.energymonitoring.application.services.EnergyMonitoringApplicationService;
import com.teralume.energycore.energymonitoring.application.services.EnergySamplingSettingsService;
import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;
import com.teralume.energycore.energymonitoring.domain.model.queries.GetEnergyDashboardSummaryQuery;
import com.teralume.energycore.energymonitoring.domain.model.queries.GetEnergyReadingsByUserQuery;
import com.teralume.energycore.energymonitoring.domain.model.queries.GetEnergySamplingSettingsQuery;
import com.teralume.energycore.energymonitoring.interfaces.rest.resources.EnergyDashboardSummaryResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnergyMonitoringQueryServiceImpl implements EnergyMonitoringQueryService {
    private final EnergyMonitoringApplicationService applicationService;
    private final EnergySamplingSettingsService settingsService;

    @Override
    public List<EnergyReading> handle(GetEnergyReadingsByUserQuery query) {
        if (query.start() != null && query.end() != null) {
            return applicationService.getReadings(query.userId(), query.start(), query.end());
        }

        return applicationService.getReadings(query.userId());
    }

    @Override
    public EnergyDashboardSummaryResource handle(GetEnergyDashboardSummaryQuery query) {
        return applicationService.getDashboardSummary(query.userId());
    }

    @Override
    public Integer handle(GetEnergySamplingSettingsQuery query) {
        return settingsService.getSampleSeconds();
    }
}
