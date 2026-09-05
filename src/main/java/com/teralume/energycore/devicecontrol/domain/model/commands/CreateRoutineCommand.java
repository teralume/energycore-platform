package com.teralume.energycore.devicecontrol.domain.model.commands;

import com.teralume.energycore.devicecontrol.domain.model.RoutineAction;
import com.teralume.energycore.devicecontrol.domain.model.RoutineRepeatType;
import com.teralume.energycore.devicecontrol.domain.model.RoutineTargetType;

public record CreateRoutineCommand(
        Long userId,
        Long deviceId,
        Long groupId,
        RoutineTargetType targetType,
        Long targetId,
        String name,
        RoutineAction action,
        String time,
        RoutineRepeatType repeatType,
        String daysOfWeek,
        Integer intervalDays,
        String startsOn
) {
}
