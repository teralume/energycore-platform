package com.teralume.energycore.devicecontrol.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOperationModeResource(
        @NotNull Long locationId,
        @NotBlank String name,
        String description,
        List<Long> roomIds,
        List<Long> groupIds,
        List<Long> deviceIds,
        List<Long> turnOnDeviceIds,
        List<Long> turnOffDeviceIds,
        List<Long> keepOnDeviceIds,
        List<Long> routineIds,
        List<Long> routinesToEnableIds,
        List<Long> routinesToDisableIds,
        List<Long> goalIds,
        List<OperationModeRoutineResource> internalRoutines,
        Boolean allDay,
        String startsAt,
        String endsAt,
        Long ruleProfileId,
        Long preferenceId,
        Boolean applyRuleProfile,
        Boolean applyNotificationPreference,
        Boolean applyRoutines,
        Boolean preserveCriticalSound
) {
}
