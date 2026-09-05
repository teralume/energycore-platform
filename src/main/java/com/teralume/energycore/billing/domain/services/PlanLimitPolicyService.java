package com.teralume.energycore.billing.domain.services;

import com.teralume.energycore.billing.domain.model.aggregates.Plan;
import org.springframework.stereotype.Service;

@Service
public class PlanLimitPolicyService {

    public boolean canCreateDevice(Plan plan, int currentDeviceCount) {
        return plan != null
                && plan.getMaxDevices() != null
                && currentDeviceCount < plan.getMaxDevices();
    }

    public boolean canCreateRoutine(Plan plan, int currentRoutineCount) {
        return plan != null
                && plan.getMaxRoutines() != null
                && currentRoutineCount < plan.getMaxRoutines();
    }

    public boolean canCreateAlert(Plan plan, int currentAlertCount) {
        return plan != null
                && plan.getMaxAlerts() != null
                && currentAlertCount < plan.getMaxAlerts();
    }

    public boolean canExportReports(Plan plan) {
        return plan != null && Boolean.TRUE.equals(plan.getReportExportEnabled());
    }
}