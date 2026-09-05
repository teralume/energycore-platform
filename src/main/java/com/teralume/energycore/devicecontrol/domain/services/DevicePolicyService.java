package com.teralume.energycore.devicecontrol.domain.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DevicePolicyService {

    public void validateDeviceData(String name, String type, BigDecimal powerWatts) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Device name is required.");
        }

        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Device type is required.");
        }

        if (powerWatts == null || powerWatts.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Device power watts must be greater than zero.");
        }
    }

    public boolean canToggleDevice(String status) {
        return status != null && !status.isBlank();
    }
}