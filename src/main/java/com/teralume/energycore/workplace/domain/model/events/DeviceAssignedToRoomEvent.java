package com.teralume.energycore.workplace.domain.model.events;

import com.teralume.energycore.shared.domain.model.DomainEvent;

import java.time.LocalDateTime;

public record DeviceAssignedToRoomEvent(Long userId, Long deviceId, Long roomId, LocalDateTime occurredOn)
        implements DomainEvent {
    public DeviceAssignedToRoomEvent(Long userId, Long deviceId, Long roomId) {
        this(userId, deviceId, roomId, LocalDateTime.now());
    }
}