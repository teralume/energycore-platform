package com.teralume.energycore.devicecontrol.domain.repositories;

import com.teralume.energycore.devicecontrol.domain.model.entities.DeviceGroupDevice;

import java.util.List;

public interface DeviceGroupDeviceRepository {
    List<DeviceGroupDevice> findByDeviceGroupId(Long deviceGroupId);

    DeviceGroupDevice save(DeviceGroupDevice relation);

    void deleteByDeviceGroupId(Long deviceGroupId);

    void deleteByDeviceId(Long deviceId);
}
