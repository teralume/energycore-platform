package com.teralume.energycore.reporting.domain.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class EnergyGoalProgressService {

    public BigDecimal calculateProgressPercentage(BigDecimal currentKilowattHours, BigDecimal targetKilowattHours) {
        if (currentKilowattHours == null || targetKilowattHours == null || targetKilowattHours.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal progress = currentKilowattHours
                .divide(targetKilowattHours, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        if (progress.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }

        if (progress.compareTo(BigDecimal.valueOf(100)) > 0) {
            return BigDecimal.valueOf(100);
        }

        return progress.setScale(2, RoundingMode.HALF_UP);
    }

    public boolean isCompleted(BigDecimal currentKilowattHours, BigDecimal targetKilowattHours) {
        return currentKilowattHours != null
                && targetKilowattHours != null
                && currentKilowattHours.compareTo(targetKilowattHours) <= 0;
    }
}
