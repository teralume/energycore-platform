package com.teralume.energycore.energymonitoring.domain.model.commands;

import java.time.LocalDateTime;

public record GetEnergyStatisticsCommand(
        Long userId,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}