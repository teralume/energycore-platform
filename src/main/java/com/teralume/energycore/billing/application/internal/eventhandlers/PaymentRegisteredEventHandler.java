package com.teralume.energycore.billing.application.internal.eventhandlers;

import com.teralume.energycore.billing.domain.model.events.PaymentRegisteredEvent;
import com.teralume.energycore.billing.interfaces.events.PaymentRegisteredIntegrationEvent;
import com.teralume.energycore.shared.application.events.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PaymentRegisteredEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void on(PaymentRegisteredEvent event) {
        integrationEventPublisher.publish(PaymentRegisteredIntegrationEvent.from(event));
    }
}
