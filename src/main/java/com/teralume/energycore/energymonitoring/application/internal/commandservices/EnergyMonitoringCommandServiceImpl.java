package com.teralume.energycore.energymonitoring.application.internal.commandservices;

import com.teralume.energycore.energymonitoring.application.commandservices.EnergyMonitoringCommandService;
import com.teralume.energycore.energymonitoring.application.services.EnergyMonitoringApplicationService;
import com.teralume.energycore.energymonitoring.application.services.EnergySamplingSettingsService;
import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;
import com.teralume.energycore.energymonitoring.domain.model.commands.CreateEnergyReadingCommand;
import com.teralume.energycore.energymonitoring.domain.model.commands.UpdateEnergySamplingSettingsCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnergyMonitoringCommandServiceImpl implements EnergyMonitoringCommandService {
    private final EnergyMonitoringApplicationService applicationService;
    private final EnergySamplingSettingsService settingsService;

    @Override
    public EnergyReading handle(CreateEnergyReadingCommand command) {
        return applicationService.createReading(command);
    }

    @Override
    public Integer handle(UpdateEnergySamplingSettingsCommand command) {
        return settingsService.updateSampleSeconds(command.sampleSeconds());
    }
}
