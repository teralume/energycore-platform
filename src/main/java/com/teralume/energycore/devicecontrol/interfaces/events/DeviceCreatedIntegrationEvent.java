package com.teralume.energycore.devicecontrol.interfaces.events;

import com.teralume.energycore.devicecontrol.domain.model.events.DeviceCreatedEvent;
import com.teralume.energycore.shared.application.events.IntegrationEvent;

import java.time.LocalDateTime;

public record DeviceCreatedIntegrationEvent(
        Long userId,
        Long deviceId,
        String deviceName,
        LocalDateTime occurredOn
) implements IntegrationEvent {
    public static DeviceCreatedIntegrationEvent from(DeviceCreatedEvent event) {
        return new DeviceCreatedIntegrationEvent(
                event.userId(),
                event.deviceId(),
                event.deviceName(),
                event.occurredOn()
        );
    }
}
