package com.teralume.energycore.servicemanagement.domain.model.events;

import com.teralume.energycore.shared.domain.model.DomainEvent;

import java.time.LocalDateTime;

public record SupportTicketCreatedEvent(Long userId, Long ticketId, String priority, LocalDateTime occurredOn)
        implements DomainEvent {
    public SupportTicketCreatedEvent(Long userId, Long ticketId, String priority) {
        this(userId, ticketId, priority, LocalDateTime.now());
    }
}