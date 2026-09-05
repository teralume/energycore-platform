package com.teralume.energycore.energymonitoring.domain.services;

import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnergyReadingFilterService {

    public List<EnergyReading> filterByDevice(List<EnergyReading> readings, Long deviceId) {
        if (readings == null) {
            return List.of();
        }

        if (deviceId == null) {
            return readings;
        }

        return readings.stream()
                .filter(reading -> deviceId.equals(reading.getDeviceId()))
                .toList();
    }

    public List<EnergyReading> filterByDateRange(
            List<EnergyReading> readings,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        if (readings == null) {
            return List.of();
        }

        return readings.stream()
                .filter(reading -> startDate == null || !reading.getRecordedAt().isBefore(startDate))
                .filter(reading -> endDate == null || !reading.getRecordedAt().isAfter(endDate))
                .toList();
    }
}