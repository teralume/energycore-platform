package com.teralume.energycore.workplace.interfaces.events;

import com.teralume.energycore.shared.application.events.IntegrationEvent;
import com.teralume.energycore.workplace.domain.model.events.DeviceAssignedToRoomEvent;

import java.time.LocalDateTime;

public record DeviceAssignedToRoomIntegrationEvent(
        Long userId,
        Long deviceId,
        Long roomId,
        LocalDateTime occurredOn
) implements IntegrationEvent {
    public static DeviceAssignedToRoomIntegrationEvent from(DeviceAssignedToRoomEvent event) {
        return new DeviceAssignedToRoomIntegrationEvent(
                event.userId(),
                event.deviceId(),
                event.roomId(),
                event.occurredOn()
        );
    }
}
