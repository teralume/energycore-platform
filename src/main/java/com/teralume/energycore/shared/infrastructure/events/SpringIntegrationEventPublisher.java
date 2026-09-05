package com.teralume.energycore.shared.infrastructure.events;

import com.teralume.energycore.shared.application.events.IntegrationEvent;
import com.teralume.energycore.shared.application.events.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringIntegrationEventPublisher implements IntegrationEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(IntegrationEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
