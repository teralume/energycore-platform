package com.teralume.energycore.energymonitoring.domain.services;

import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EnergyRecommendationService {

    public String resolveRecommendation(List<EnergyReading> readings) {
        if (readings == null || readings.isEmpty()) {
            return "No energy readings available.";
        }

        BigDecimal total = readings.stream()
                .map(EnergyReading::getWatts)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (total.compareTo(BigDecimal.valueOf(1000)) > 0) {
            return "High energy consumption detected. Consider turning off inactive devices.";
        }

        return "Energy consumption is within a normal range.";
    }
}