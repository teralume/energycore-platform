package com.teralume.energycore.devicecontrol.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.DeviceGroup;
import com.teralume.energycore.devicecontrol.domain.repositories.DeviceGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDeviceGroupRepository extends JpaRepository<DeviceGroup, Long>, DeviceGroupRepository {
}
