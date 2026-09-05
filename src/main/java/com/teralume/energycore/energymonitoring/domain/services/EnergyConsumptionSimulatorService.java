package com.teralume.energycore.energymonitoring.domain.services;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.Device;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class EnergyConsumptionSimulatorService {

    public BigDecimal simulateWatts(Device device) {
        BigDecimal baseWatts = device.getPowerWatts();

        if (baseWatts == null || baseWatts.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        return baseWatts.setScale(2, RoundingMode.HALF_UP);
    }
}