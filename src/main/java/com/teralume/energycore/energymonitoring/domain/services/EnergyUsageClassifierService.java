package com.teralume.energycore.energymonitoring.domain.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EnergyUsageClassifierService {

    public String classify(BigDecimal watts) {
        if (watts == null) {
            return "UNKNOWN";
        }

        if (watts.compareTo(BigDecimal.valueOf(100)) < 0) {
            return "LOW";
        }

        if (watts.compareTo(BigDecimal.valueOf(500)) < 0) {
            return "NORMAL";
        }

        if (watts.compareTo(BigDecimal.valueOf(1000)) < 0) {
            return "HIGH";
        }

        return "CRITICAL";
    }
}