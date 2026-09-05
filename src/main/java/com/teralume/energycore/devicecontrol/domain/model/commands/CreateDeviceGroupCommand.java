package com.teralume.energycore.devicecontrol.domain.model.commands;

import java.util.List;

public record CreateDeviceGroupCommand(
        Long userId,
        String name,
        String description,
        List<Long> deviceIds
) {
}