package com.teralume.energycore.energymonitoring.application.commandservices;

import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;
import com.teralume.energycore.energymonitoring.domain.model.commands.CreateEnergyReadingCommand;
import com.teralume.energycore.energymonitoring.domain.model.commands.UpdateEnergySamplingSettingsCommand;

public interface EnergyMonitoringCommandService {
    EnergyReading handle(CreateEnergyReadingCommand command);
    Integer handle(UpdateEnergySamplingSettingsCommand command);
}
