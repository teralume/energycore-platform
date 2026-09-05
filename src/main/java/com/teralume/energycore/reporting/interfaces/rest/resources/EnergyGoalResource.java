package com.teralume.energycore.reporting.interfaces.rest.resources;

import com.teralume.energycore.reporting.domain.model.aggregates.EnergyGoal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record EnergyGoalResource(
        Long id,
        Long userId,
        String title,
        BigDecimal targetKilowattHours,
        BigDecimal currentKilowattHours,
        LocalDate deadline,
        String status,
        LocalDate createdAt,
        String scopeType,
        Long scopeId,
        String scopeName,
        LocalTime activeFrom,
        LocalTime activeTo
) {
    public static EnergyGoalResource from(EnergyGoal goal) {
        return new EnergyGoalResource(
                goal.getId(),
                goal.getUserId(),
                goal.getTitle(),
                goal.getTargetKilowattHours(),
                goal.getCurrentKilowattHours(),
                goal.getDeadline(),
                goal.getStatus(),
                goal.getCreatedAt().toLocalDate(),
                goal.getScopeType() != null ? goal.getScopeType() : "GENERAL",
                goal.getScopeId(),
                goal.getScopeName(),
                goal.getActiveFrom(),
                goal.getActiveTo()
        );
    }
}
