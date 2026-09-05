package com.teralume.energycore.energymonitoring.domain.repositories;

import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;

import java.time.LocalDateTime;
import java.util.List;

public interface EnergyReadingRepository {
    List<EnergyReading> findByUserIdAndRecordedAtBetween(
        Long userId,
        LocalDateTime start,
        LocalDateTime end
);

    List<EnergyReading> findByUserIdOrderByRecordedAtDesc(Long userId);

    List<EnergyReading> findByUserIdAndRecordedAtBetweenOrderByRecordedAtDesc(
            Long userId,
            LocalDateTime start,
            LocalDateTime end
    );

    EnergyReading save(EnergyReading energyReading);
}
