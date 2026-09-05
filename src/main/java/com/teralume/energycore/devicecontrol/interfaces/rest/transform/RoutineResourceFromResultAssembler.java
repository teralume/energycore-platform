package com.teralume.energycore.devicecontrol.interfaces.rest.transform;

import com.teralume.energycore.devicecontrol.application.results.RoutineDetails;
import com.teralume.energycore.devicecontrol.interfaces.rest.resources.RoutineResource;

public class RoutineResourceFromResultAssembler {
    public static RoutineResource toResourceFromResult(RoutineDetails details) {
        var routine = details.routine();
        return new RoutineResource(
                routine.getId(),
                routine.getUserId(),
                routine.getDeviceId(),
                routine.getGroupId(),
                routine.getTargetType(),
                routine.getTargetId(),
                details.targetName(),
                routine.getName(),
                routine.getAction(),
                routine.getTime(),
                routine.getEffectiveRepeatType(),
                routine.getDaysOfWeek(),
                routine.getEffectiveIntervalDays(),
                routine.getStartsOn(),
                details.applicableDeviceCount(),
                details.blockedDeviceCount(),
                routine.getEnabled()
        );
    }
}
