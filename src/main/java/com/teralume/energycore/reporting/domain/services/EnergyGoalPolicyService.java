package com.teralume.energycore.reporting.domain.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class EnergyGoalPolicyService {

    public boolean canCreateGoal(String title, BigDecimal targetKilowattHours, LocalDate deadline) {
        return title != null
                && !title.isBlank()
                && targetKilowattHours != null
                && targetKilowattHours.compareTo(BigDecimal.ZERO) > 0
                && deadline != null
                && deadline.isAfter(LocalDate.now());
    }
}
