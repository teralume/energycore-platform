package com.teralume.energycore.shared.application.events;

import com.teralume.energycore.shared.domain.model.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
