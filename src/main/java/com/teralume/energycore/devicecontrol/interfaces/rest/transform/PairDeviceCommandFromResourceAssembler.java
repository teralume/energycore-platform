package com.teralume.energycore.devicecontrol.interfaces.rest.transform;

import com.teralume.energycore.devicecontrol.domain.model.commands.PairDeviceCommand;
import com.teralume.energycore.devicecontrol.interfaces.rest.resources.PairDeviceResource;

public class PairDeviceCommandFromResourceAssembler {
    public static PairDeviceCommand toCommandFromResource(PairDeviceResource resource, Long userId) {
        return new PairDeviceCommand(
                userId,
                resource.pairingCode(),
                resource.alias(),
                resource.room()
        );
    }
}
