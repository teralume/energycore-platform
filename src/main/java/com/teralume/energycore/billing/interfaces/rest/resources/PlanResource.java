package com.teralume.energycore.billing.interfaces.rest.resources;

import com.teralume.energycore.billing.domain.model.aggregates.Plan;

import java.math.BigDecimal;

public record PlanResource(
        Long id,
        String code,
        String name,
        BigDecimal monthlyPrice,
        String currency,
        Integer maxDevices,
        Integer maxRoutines,
        Integer maxAlerts,
        Boolean reportExportEnabled
) {
    public static PlanResource from(Plan plan) {
        return new PlanResource(
                plan.getId(),
                plan.getCode(),
                plan.getName(),
                plan.getMonthlyPrice() != null ? plan.getMonthlyPrice().getAmount() : null,
                plan.getMonthlyPrice() != null ? plan.getMonthlyPrice().getCurrency() : null,
                plan.getMaxDevices(),
                plan.getMaxRoutines(),
                plan.getMaxAlerts(),
                plan.getReportExportEnabled()
        );
    }
}