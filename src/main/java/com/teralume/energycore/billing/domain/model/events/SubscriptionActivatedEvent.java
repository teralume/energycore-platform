package com.teralume.energycore.billing.domain.model.events;

import com.teralume.energycore.shared.domain.model.DomainEvent;

import java.time.LocalDateTime;

public record SubscriptionActivatedEvent(Long userId, Long subscriptionId, String planCode, LocalDateTime occurredOn)
        implements DomainEvent {
    public SubscriptionActivatedEvent(Long userId, Long subscriptionId, String planCode) {
        this(userId, subscriptionId, planCode, LocalDateTime.now());
    }
}