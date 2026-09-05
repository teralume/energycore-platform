package com.teralume.energycore.devicecontrol.domain.model.events;

import com.teralume.energycore.shared.domain.model.DomainEvent;

import java.time.LocalDateTime;

public record DeviceCreatedEvent(Long userId, Long deviceId, String deviceName, LocalDateTime occurredOn)
        implements DomainEvent {
    public DeviceCreatedEvent(Long userId, Long deviceId, String deviceName) {
        this(userId, deviceId, deviceName, LocalDateTime.now());
    }
}