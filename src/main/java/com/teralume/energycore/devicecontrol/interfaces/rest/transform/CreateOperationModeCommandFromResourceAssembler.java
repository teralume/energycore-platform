package com.teralume.energycore.devicecontrol.interfaces.rest.transform;

import com.teralume.energycore.devicecontrol.domain.model.RoutineAction;
import com.teralume.energycore.devicecontrol.domain.model.RoutineTargetType;
import com.teralume.energycore.devicecontrol.domain.model.commands.CreateOperationModeCommand;
import com.teralume.energycore.devicecontrol.domain.model.commands.OperationModeRoutineCommand;
import com.teralume.energycore.devicecontrol.interfaces.rest.resources.CreateOperationModeResource;
import com.teralume.energycore.devicecontrol.interfaces.rest.resources.OperationModeRoutineResource;

import java.util.List;

public class CreateOperationModeCommandFromResourceAssembler {
    public static CreateOperationModeCommand toCommandFromResource(CreateOperationModeResource resource, Long userId) {
        return new CreateOperationModeCommand(
                userId,
                resource.locationId(),
                resource.name(),
                resource.description(),
                resource.roomIds(),
                resource.groupIds(),
                resource.deviceIds(),
                resource.turnOnDeviceIds(),
                resource.turnOffDeviceIds(),
                resource.keepOnDeviceIds(),
                resource.routineIds(),
                resource.routinesToEnableIds(),
                resource.routinesToDisableIds(),
                resource.goalIds(),
                toRoutineCommands(resource.internalRoutines()),
                resource.allDay(),
                resource.startsAt(),
                resource.endsAt(),
                resource.ruleProfileId(),
                resource.preferenceId(),
                resource.applyRuleProfile(),
                resource.applyNotificationPreference(),
                resource.applyRoutines(),
                resource.preserveCriticalSound()
        );
    }

    private static List<OperationModeRoutineCommand> toRoutineCommands(List<OperationModeRoutineResource> resources) {
        if (resources == null) {
            return List.of();
        }

        return resources.stream()
                .map(CreateOperationModeCommandFromResourceAssembler::toRoutineCommand)
                .toList();
    }

    private static OperationModeRoutineCommand toRoutineCommand(OperationModeRoutineResource resource) {
        return new OperationModeRoutineCommand(
                resource.name(),
                RoutineTargetType.valueOf(resource.targetType()),
                resource.targetId(),
                RoutineAction.valueOf(resource.action()),
                resource.triggerTime(),
                resource.enabled()
        );
    }
}
