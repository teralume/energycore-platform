package com.teralume.energycore.iam.application.internal.eventhandlers;

import com.teralume.energycore.iam.domain.model.events.UserRegisteredEvent;
import com.teralume.energycore.iam.interfaces.events.UserRegisteredIntegrationEvent;
import com.teralume.energycore.shared.application.events.IntegrationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void on(UserRegisteredEvent event) {
        integrationEventPublisher.publish(UserRegisteredIntegrationEvent.from(event));
    }
}
