package com.teralume.energycore.servicemanagement.interfaces.rest.transform;

import com.teralume.energycore.servicemanagement.domain.model.aggregates.SupportTicket;
import com.teralume.energycore.servicemanagement.interfaces.rest.resources.SupportTicketResource;

public class SupportTicketResourceFromEntityAssembler {
    public static SupportTicketResource toResourceFromEntity(SupportTicket ticket) {
        return new SupportTicketResource(
                ticket.getId(),
                ticket.getUserId(),
                ticket.getSubject(),
                ticket.getDescription(),
                ticket.getPriority(),
                ticket.getStatus(),
                ticket.getCreatedAt() != null ? ticket.getCreatedAt().toLocalDate() : null
        );
    }
}
