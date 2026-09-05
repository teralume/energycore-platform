package com.teralume.energycore.billing.interfaces.events;

import com.teralume.energycore.billing.domain.model.events.SubscriptionActivatedEvent;
import com.teralume.energycore.shared.application.events.IntegrationEvent;

import java.time.LocalDateTime;

public record SubscriptionActivatedIntegrationEvent(
        Long userId,
        Long subscriptionId,
        String planCode,
        LocalDateTime occurredOn
) implements IntegrationEvent {
    public static SubscriptionActivatedIntegrationEvent from(SubscriptionActivatedEvent event) {
        return new SubscriptionActivatedIntegrationEvent(
                event.userId(),
                event.subscriptionId(),
                event.planCode(),
                event.occurredOn()
        );
    }
}
