package com.teralume.energycore.energymonitoring.domain.model.commands;

import java.math.BigDecimal;

public record CreateEnergyReadingCommand(
        Long userId,
        Long deviceId,
        String deviceName,
        BigDecimal watts
) {
}