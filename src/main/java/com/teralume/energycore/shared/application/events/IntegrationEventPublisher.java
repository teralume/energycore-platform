package com.teralume.energycore.shared.application.events;

public interface IntegrationEventPublisher {
    void publish(IntegrationEvent event);
}
