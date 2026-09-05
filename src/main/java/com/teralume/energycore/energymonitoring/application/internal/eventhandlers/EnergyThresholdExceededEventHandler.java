package com.teralume.energycore.energymonitoring.application.internal.eventhandlers;

import com.teralume.energycore.energymonitoring.domain.model.events.EnergyThresholdExceededEvent;
import com.teralume.energycore.energymonitoring.interfaces.events.EnergyThresholdExceededIntegrationEvent;
import com.teralume.energycore.shared.application.events.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EnergyThresholdExceededEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void on(EnergyThresholdExceededEvent event) {
        integrationEventPublisher.publish(EnergyThresholdExceededIntegrationEvent.from(event));
    }
}
