package com.teralume.energycore.energymonitoring.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;
import com.teralume.energycore.energymonitoring.domain.repositories.EnergyReadingRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEnergyReadingRepository extends JpaRepository<EnergyReading, Long>, EnergyReadingRepository {
}
