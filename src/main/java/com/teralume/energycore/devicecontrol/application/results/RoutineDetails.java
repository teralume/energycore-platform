package com.teralume.energycore.devicecontrol.application.results;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.Routine;

public record RoutineDetails(
        Routine routine,
        String targetName,
        int applicableDeviceCount,
        int blockedDeviceCount
) {
}
