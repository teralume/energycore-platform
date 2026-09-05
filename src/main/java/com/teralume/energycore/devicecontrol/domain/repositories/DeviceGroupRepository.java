package com.teralume.energycore.devicecontrol.domain.repositories;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.DeviceGroup;

import java.util.List;
import java.util.Optional;

public interface DeviceGroupRepository {
    List<DeviceGroup> findByUserId(Long userId);

    Optional<DeviceGroup> findById(Long id);

    DeviceGroup save(DeviceGroup deviceGroup);

    void delete(DeviceGroup deviceGroup);
}
