package com.teralume.energycore.workplace.interfaces.rest.resources;

import com.teralume.energycore.workplace.domain.model.aggregates.DeviceAssignment;

import java.time.LocalDateTime;

public record DeviceAssignmentResource(
        Long id,
        Long deviceId,
        Long roomId,
        Long locationId,
        LocalDateTime assignedAt
) {
    public static DeviceAssignmentResource from(DeviceAssignment assignment) {
        return new DeviceAssignmentResource(
                assignment.getId(),
                assignment.getDeviceId(),
                assignment.getRoomId(),
                assignment.getLocationId(),
                assignment.getAssignedAt()
        );
    }
}