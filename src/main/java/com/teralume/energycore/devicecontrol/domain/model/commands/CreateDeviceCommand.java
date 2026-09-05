package com.teralume.energycore.devicecontrol.domain.model.commands;

import java.math.BigDecimal;

public record CreateDeviceCommand(
        Long userId,
        String name,
        String room,
        String type,
        BigDecimal powerWatts
) {
}