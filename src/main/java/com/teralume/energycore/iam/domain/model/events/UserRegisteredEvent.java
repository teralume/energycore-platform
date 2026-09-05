package com.teralume.energycore.iam.domain.model.events;

import com.teralume.energycore.shared.domain.model.DomainEvent;

import java.time.LocalDateTime;

public record UserRegisteredEvent(Long userId, String email, LocalDateTime occurredOn) implements DomainEvent {
    public UserRegisteredEvent(Long userId, String email) {
        this(userId, email, LocalDateTime.now());
    }
}