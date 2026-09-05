package com.teralume.energycore.iam.application.internal.eventhandlers;

import com.teralume.energycore.iam.domain.model.events.UserRegisteredEvent;
import com.teralume.energycore.iam.interfaces.events.UserRegisteredIntegrationEvent;
import com.teralume.energycore.shared.application.events.IntegrationEventPublisher;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserRegisteredEventHandlerTest {

    @Test
    void translatesDomainEventIntoIntegrationEvent() {
        IntegrationEventPublisher publisher = mock(IntegrationEventPublisher.class);
        UserRegisteredEventHandler handler = new UserRegisteredEventHandler(publisher);

        handler.on(new UserRegisteredEvent(42L, "member@energycore.pe"));

        verify(publisher).publish(isA(UserRegisteredIntegrationEvent.class));
    }
}
