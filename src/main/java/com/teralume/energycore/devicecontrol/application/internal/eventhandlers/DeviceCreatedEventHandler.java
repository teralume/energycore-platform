package com.teralume.energycore.devicecontrol.application.internal.eventhandlers;

import com.teralume.energycore.devicecontrol.domain.model.events.DeviceCreatedEvent;
import com.teralume.energycore.devicecontrol.interfaces.events.DeviceCreatedIntegrationEvent;
import com.teralume.energycore.shared.application.events.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class DeviceCreatedEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void on(DeviceCreatedEvent event) {
        integrationEventPublisher.publish(DeviceCreatedIntegrationEvent.from(event));
    }
}
