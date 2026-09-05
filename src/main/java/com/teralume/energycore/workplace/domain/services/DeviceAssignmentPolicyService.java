package com.teralume.energycore.workplace.domain.services;

import org.springframework.stereotype.Service;

@Service
public class DeviceAssignmentPolicyService {

    public void validateAssignment(Long deviceId, Long roomId, Long locationId) {
        if (deviceId == null) {
            throw new IllegalArgumentException("Device id is required.");
        }

        if (locationId == null) {
            throw new IllegalArgumentException("Location id is required.");
        }
    }

    public boolean canAssignDevice(Long deviceId, Long roomId, Long locationId) {
        return deviceId != null && locationId != null;
    }
}
