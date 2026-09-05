package com.teralume.energycore.devicecontrol.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.devicecontrol.domain.model.entities.DeviceGroupDevice;
import com.teralume.energycore.devicecontrol.domain.repositories.DeviceGroupDeviceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDeviceGroupDeviceRepository extends JpaRepository<DeviceGroupDevice, Long>, DeviceGroupDeviceRepository {
}
