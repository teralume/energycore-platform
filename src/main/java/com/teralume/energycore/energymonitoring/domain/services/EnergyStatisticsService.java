package com.teralume.energycore.energymonitoring.domain.services;

import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class EnergyStatisticsService {

    public BigDecimal calculateTotalWatts(List<EnergyReading> readings) {
        if (readings == null || readings.isEmpty()) return BigDecimal.ZERO;

        return readings.stream()
                .map(EnergyReading::getWatts)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateAverageWatts(List<EnergyReading> readings) {
        if (readings == null || readings.isEmpty()) return BigDecimal.ZERO;

        return calculateTotalWatts(readings)
                .divide(BigDecimal.valueOf(readings.size()), 2, RoundingMode.HALF_UP);
    }
}