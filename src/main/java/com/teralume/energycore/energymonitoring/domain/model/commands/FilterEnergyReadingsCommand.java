package com.teralume.energycore.energymonitoring.domain.model.commands;

import java.time.LocalDateTime;

public record FilterEnergyReadingsCommand(
        Long userId,
        Long deviceId,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}