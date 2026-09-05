package com.teralume.energycore.devicecontrol.application.results;

import java.util.List;

public record OperationModePreviewResult(
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
}
