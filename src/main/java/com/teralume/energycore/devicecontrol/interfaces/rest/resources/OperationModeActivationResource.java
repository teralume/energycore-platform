package com.teralume.energycore.devicecontrol.interfaces.rest.resources;

import com.teralume.energycore.devicecontrol.application.results.OperationModeActivationResult;

import java.util.List;

public record OperationModeActivationResource(
        OperationModeResource mode,
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
    public static OperationModeActivationResource from(OperationModeActivationResult result) {
        return new OperationModeActivationResource(
                OperationModeResource.from(result.mode()),
                result.turnedOnDeviceIds(),
                result.turnedOffDeviceIds(),
                result.ignoredRemovedDeviceIds(),
                result.ignoredMaintenanceDeviceIds(),
                result.enabledRoutineIds(),
                result.disabledRoutineIds(),
                result.evidence(),
                result.explanation(),
                result.recommendedAction()
        );
    }
}
