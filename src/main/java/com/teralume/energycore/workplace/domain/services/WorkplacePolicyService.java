package com.teralume.energycore.workplace.domain.services;

import org.springframework.stereotype.Service;

@Service
public class WorkplacePolicyService {

    public boolean canCreateLocation(String name) {
        return name != null && !name.isBlank();
    }

    public boolean canCreateRoom(Long locationId, String name) {
        return locationId != null && name != null && !name.isBlank();
    }

    public boolean canAssignDevice(Long deviceId, Long roomId, Long locationId) {
        return deviceId != null && locationId != null;
    }
}
