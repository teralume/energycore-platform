package com.teralume.energycore.workplace.domain.repositories;

import com.teralume.energycore.workplace.domain.model.aggregates.DeviceAssignment;

import java.util.List;
import java.util.Optional;

public interface DeviceAssignmentRepository {
    List<DeviceAssignment> findByLocationId(Long locationId);

    List<DeviceAssignment> findByLocationIdIn(List<Long> locationIds);

    List<DeviceAssignment> findByDeviceId(Long deviceId);

    List<DeviceAssignment> findByRoomId(Long roomId);

    Optional<DeviceAssignment> findById(Long id);

    DeviceAssignment save(DeviceAssignment assignment);

    void delete(DeviceAssignment assignment);

    void deleteByLocationId(Long locationId);
}
