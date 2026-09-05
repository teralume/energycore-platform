package com.teralume.energycore.devicecontrol.domain.model.commands;

import java.util.List;

public record UpdateDeviceGroupCommand(
        String name,
        String description,
        List<Long> deviceIds
) {
}