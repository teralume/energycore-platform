package com.teralume.energycore.energymonitoring.domain.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EnergyReadingPolicyService {

    public void validateReading(Long userId, Long deviceId, String deviceName, BigDecimal watts) {
        if (userId == null) {
            throw new IllegalArgumentException("User id is required.");
        }

        if (deviceId == null) {
            throw new IllegalArgumentException("Device id is required.");
        }

        if (deviceName == null || deviceName.isBlank()) {
            throw new IllegalArgumentException("Device name is required.");
        }

        if (watts == null || watts.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Watts must be zero or greater.");
        }
    }

    public boolean isHighConsumption(BigDecimal watts) {
        return watts != null && watts.compareTo(BigDecimal.valueOf(1000)) >= 0;
    }

    public boolean isLowConsumption(BigDecimal watts) {
        return watts != null && watts.compareTo(BigDecimal.valueOf(100)) < 0;
    }
}