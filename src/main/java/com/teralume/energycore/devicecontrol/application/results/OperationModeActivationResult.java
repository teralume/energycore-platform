package com.teralume.energycore.devicecontrol.application.results;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.OperationMode;

import java.util.List;

public record OperationModeActivationResult(
        OperationMode mode,
        List<Long> turnedOnDeviceIds,
        List<Long> turnedOffDeviceIds,
        List<Long> ignoredRemovedDeviceIds,
        List<Long> ignoredMaintenanceDeviceIds,
        List<Long> enabledRoutineIds,
        List<Long> disabledRoutineIds,
        String evidence,
        String explanation,
        String recommendedAction
) {
}
