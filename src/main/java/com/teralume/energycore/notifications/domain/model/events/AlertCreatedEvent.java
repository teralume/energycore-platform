package com.teralume.energycore.notifications.domain.model.events;

import com.teralume.energycore.shared.domain.model.DomainEvent;

import java.time.LocalDateTime;

public record AlertCreatedEvent(Long userId, Long alertId, String severity, LocalDateTime occurredOn)
        implements DomainEvent {
    public AlertCreatedEvent(Long userId, Long alertId, String severity) {
        this(userId, alertId, severity, LocalDateTime.now());
    }
}