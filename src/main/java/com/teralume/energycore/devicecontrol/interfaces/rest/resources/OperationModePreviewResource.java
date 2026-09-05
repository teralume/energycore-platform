package com.teralume.energycore.devicecontrol.interfaces.rest.resources;

import com.teralume.energycore.devicecontrol.application.results.OperationModePreviewResult;

import java.util.List;

public record OperationModePreviewResource(
        Long modeId,
        Long locationId,
        String locationName,
        List<Long> affectedDeviceIds,
        List<Long> ignoredRemovedDeviceIds,
        List<Long> ignoredMaintenanceDeviceIds,
        List<Long> roomIds,
        List<Long> groupIds,
        List<Long> routineIds,
        List<Long> goalIds,
        Long ruleProfileId,
        Long preferenceId,
        String evidence,
        String explanation,
        String recommendedAction
) {
    public static OperationModePreviewResource from(OperationModePreviewResult result) {
        return new OperationModePreviewResource(
                result.modeId(),
                result.locationId(),
                result.locationName(),
                result.affectedDeviceIds(),
                result.ignoredRemovedDeviceIds(),
                result.ignoredMaintenanceDeviceIds(),
                result.roomIds(),
                result.groupIds(),
                result.routineIds(),
                result.goalIds(),
                result.ruleProfileId(),
                result.preferenceId(),
                result.evidence(),
                result.explanation(),
                result.recommendedAction()
        );
    }
}
