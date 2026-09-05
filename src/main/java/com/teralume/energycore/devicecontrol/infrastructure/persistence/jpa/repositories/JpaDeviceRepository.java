package com.teralume.energycore.devicecontrol.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.Device;
import com.teralume.energycore.devicecontrol.domain.repositories.DeviceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDeviceRepository extends JpaRepository<Device, Long>, DeviceRepository {
}
