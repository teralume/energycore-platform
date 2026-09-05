package com.teralume.energycore.workplace.application.internal.eventhandlers;

import com.teralume.energycore.shared.application.events.IntegrationEventPublisher;
import com.teralume.energycore.workplace.domain.model.events.DeviceAssignedToRoomEvent;
import com.teralume.energycore.workplace.interfaces.events.DeviceAssignedToRoomIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class DeviceAssignedToRoomEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void on(DeviceAssignedToRoomEvent event) {
        integrationEventPublisher.publish(DeviceAssignedToRoomIntegrationEvent.from(event));
    }
}
