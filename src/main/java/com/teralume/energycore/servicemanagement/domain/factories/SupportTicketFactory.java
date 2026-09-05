package com.teralume.energycore.servicemanagement.domain.factories;

import com.teralume.energycore.servicemanagement.domain.model.aggregates.SupportTicket;
import com.teralume.energycore.servicemanagement.domain.model.valueobjects.Priority;
import com.teralume.energycore.servicemanagement.domain.model.valueobjects.TicketDescription;
import com.teralume.energycore.servicemanagement.domain.model.valueobjects.TicketTitle;

public class SupportTicketFactory {
    public SupportTicket create(Long userId, String subject, String description, String priority) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id must be positive.");
        }

        SupportTicket ticket = new SupportTicket();
        ticket.setUserId(userId);
        ticket.setSubject(new TicketTitle(subject).value());
        ticket.setDescription(new TicketDescription(description).value());
        ticket.setPriority(new Priority(priority).value());
        ticket.setStatus("OPEN");
        return ticket;
    }
}