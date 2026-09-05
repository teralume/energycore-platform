package com.teralume.energycore.devicecontrol.interfaces.rest.transform;

import com.teralume.energycore.devicecontrol.domain.model.commands.CreateRoutineCommand;
import com.teralume.energycore.devicecontrol.interfaces.rest.resources.CreateRoutineResource;

public class CreateRoutineCommandFromResourceAssembler {
    public static CreateRoutineCommand toCommandFromResource(CreateRoutineResource resource, Long userId) {
        return new CreateRoutineCommand(
                userId,
                resource.deviceId(),
                resource.groupId(),
                resource.targetType(),
                resource.targetId(),
                resource.name(),
                resource.action(),
                resource.time(),
                resource.repeatType(),
                resource.daysOfWeek(),
                resource.intervalDays(),
                resource.startsOn()
        );
    }
}
