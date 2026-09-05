package com.teralume.energycore.devicecontrol.domain.model.commands;

import java.util.List;

public record CreateOperationModeCommand(
        Long userId,
        Long locationId,
        String name,
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
        List<OperationModeRoutineCommand> internalRoutines,
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
