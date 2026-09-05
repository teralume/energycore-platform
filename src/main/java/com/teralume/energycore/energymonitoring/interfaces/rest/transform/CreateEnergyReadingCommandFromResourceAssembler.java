package com.teralume.energycore.energymonitoring.interfaces.rest.transform;

import com.teralume.energycore.energymonitoring.domain.model.commands.CreateEnergyReadingCommand;
import com.teralume.energycore.energymonitoring.interfaces.rest.resources.CreateEnergyReadingResource;

public class CreateEnergyReadingCommandFromResourceAssembler {
    public static CreateEnergyReadingCommand toCommandFromResource(CreateEnergyReadingResource resource, Long userId) {
        return new CreateEnergyReadingCommand(
                userId,
                resource.deviceId(),
                resource.deviceName(),
                resource.watts()
        );
    }
}
