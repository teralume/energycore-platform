package com.teralume.energycore.servicemanagement.application.internal.eventhandlers;

import com.teralume.energycore.servicemanagement.domain.model.events.SupportTicketCreatedEvent;
import com.teralume.energycore.servicemanagement.interfaces.events.SupportTicketCreatedIntegrationEvent;
import com.teralume.energycore.shared.application.events.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SupportTicketCreatedEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void on(SupportTicketCreatedEvent event) {
        integrationEventPublisher.publish(SupportTicketCreatedIntegrationEvent.from(event));
    }
}
