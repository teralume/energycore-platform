package com.teralume.energycore.energymonitoring.interfaces.rest.resources;

import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EnergyReadingResource(
        Long id,
        Long userId,
        Long deviceId,
        String deviceName,
        BigDecimal watts,
        BigDecimal kilowattHours,
        BigDecimal estimatedCost,
        Integer sampleSeconds,
        LocalDateTime recordedAt,
        String status
) {
    public static EnergyReadingResource from(EnergyReading reading) {
        return new EnergyReadingResource(
                reading.getId(),
                reading.getUserId(),
                reading.getDeviceId(),
                reading.getDeviceName(),
                reading.getWatts(),
                reading.getKilowattHours() != null ? reading.getKilowattHours() : BigDecimal.ZERO,
                reading.getEstimatedCost() != null ? reading.getEstimatedCost() : BigDecimal.ZERO,
                reading.getSampleSeconds() != null ? reading.getSampleSeconds() : 0,
                reading.getRecordedAt(),
                reading.getStatus() != null ? reading.getStatus().name() : "NORMAL"
        );
    }
}