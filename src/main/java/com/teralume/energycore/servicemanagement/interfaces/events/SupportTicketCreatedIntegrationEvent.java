package com.teralume.energycore.servicemanagement.interfaces.events;

import com.teralume.energycore.servicemanagement.domain.model.events.SupportTicketCreatedEvent;
import com.teralume.energycore.shared.application.events.IntegrationEvent;

import java.time.LocalDateTime;

public record SupportTicketCreatedIntegrationEvent(
        Long userId,
        Long ticketId,
        String priority,
        LocalDateTime occurredOn
) implements IntegrationEvent {
    public static SupportTicketCreatedIntegrationEvent from(SupportTicketCreatedEvent event) {
        return new SupportTicketCreatedIntegrationEvent(
                event.userId(),
                event.ticketId(),
                event.priority(),
                event.occurredOn()
        );
    }
}
