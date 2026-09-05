package com.teralume.energycore.energymonitoring.interfaces.rest.transform;

import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;
import com.teralume.energycore.energymonitoring.interfaces.rest.resources.EnergyReadingResource;

import java.math.BigDecimal;

public class EnergyReadingResourceFromEntityAssembler {
    public static EnergyReadingResource toResourceFromEntity(EnergyReading reading) {
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