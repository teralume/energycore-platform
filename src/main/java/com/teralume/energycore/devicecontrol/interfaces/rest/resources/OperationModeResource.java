package com.teralume.energycore.devicecontrol.interfaces.rest.resources;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.OperationMode;
import com.teralume.energycore.devicecontrol.domain.model.entities.OperationModeRoutine;

import java.util.List;
import java.util.Objects;

public record OperationModeResource(
        Long id,
        Long userId,
        Long locationId,
        String name,
        String description,
        String status,
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
        Boolean preserveCriticalSound,
        String lastActivatedAt
) {
    public static OperationModeResource from(OperationMode mode) {
        return new OperationModeResource(
                mode.getId(),
                mode.getUserId(),
                mode.getLocationId(),
                mode.getName(),
                mode.getDescription(),
                mode.getStatus() == null ? "DRAFT" : mode.getStatus().name(),
                CsvIds.parse(mode.getRoomIds()),
                CsvIds.parse(mode.getGroupIds()),
                CsvIds.parse(mode.getDeviceIds()),
                CsvIds.parse(mode.getTurnOnDeviceIds()),
                CsvIds.parse(mode.getTurnOffDeviceIds()),
                CsvIds.parse(mode.getKeepOnDeviceIds()),
                CsvIds.parse(mode.getRoutineIds()),
                CsvIds.parse(mode.getRoutinesToEnableIds()),
                CsvIds.parse(mode.getRoutinesToDisableIds()),
                CsvIds.parse(mode.getGoalIds()),
                safeInternalRoutines(mode).stream()
                        .filter(OperationModeResource::hasRequiredRoutineFields)
                        .map(OperationModeRoutineResource::from)
                        .toList(),
                mode.getAllDay(),
                mode.getStartsAt(),
                mode.getEndsAt(),
                mode.getRuleProfileId(),
                mode.getPreferenceId(),
                mode.getApplyRuleProfile(),
                mode.getApplyNotificationPreference(),
                mode.getApplyRoutines(),
                mode.getPreserveCriticalSound(),
                mode.getLastActivatedAt() == null ? null : mode.getLastActivatedAt().toString()
        );
    }

    private static List<OperationModeRoutine> safeInternalRoutines(OperationMode mode) {
        return mode.getInternalRoutines() == null ? List.of() : mode.getInternalRoutines();
    }

    private static boolean hasRequiredRoutineFields(OperationModeRoutine routine) {
        return routine != null &&
                routine.getTargetType() != null &&
                routine.getTargetId() != null &&
                routine.getAction() != null &&
                Objects.nonNull(routine.getTriggerTime());
    }
}
