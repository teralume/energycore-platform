package com.teralume.energycore.energymonitoring.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.energymonitoring.domain.model.EnergyDeviceUsageState;
import com.teralume.energycore.energymonitoring.domain.repositories.EnergyDeviceUsageStateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEnergyDeviceUsageStateRepository extends JpaRepository<EnergyDeviceUsageState, Long>, EnergyDeviceUsageStateRepository {
}
