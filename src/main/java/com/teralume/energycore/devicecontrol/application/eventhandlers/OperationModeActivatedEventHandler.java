package com.teralume.energycore.devicecontrol.application.eventhandlers;

import com.teralume.energycore.devicecontrol.domain.model.events.OperationModeActivatedEvent;
import com.teralume.energycore.devicecontrol.interfaces.events.OperationModeActivatedIntegrationEvent;
import com.teralume.energycore.shared.application.events.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OperationModeActivatedEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void on(OperationModeActivatedEvent event) {
        integrationEventPublisher.publish(OperationModeActivatedIntegrationEvent.from(event));
    }
}
