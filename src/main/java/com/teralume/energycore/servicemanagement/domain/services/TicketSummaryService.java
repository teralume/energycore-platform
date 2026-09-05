package com.teralume.energycore.servicemanagement.domain.services;

import com.teralume.energycore.servicemanagement.domain.model.aggregates.MaintenanceTicket;
import com.teralume.energycore.servicemanagement.domain.model.aggregates.SupportTicket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketSummaryService {

    public long countOpenSupportTickets(List<SupportTicket> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            return 0;
        }

        return tickets.stream()
                .filter(ticket -> "OPEN".equalsIgnoreCase(ticket.getStatus()))
                .count();
    }

    public long countUrgentSupportTickets(List<SupportTicket> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            return 0;
        }

        return tickets.stream()
                .filter(ticket -> "URGENT".equalsIgnoreCase(ticket.getPriority()))
                .count();
    }

    public long countOpenMaintenanceTickets(List<MaintenanceTicket> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            return 0;
        }

        return tickets.stream()
                .filter(ticket -> "OPEN".equalsIgnoreCase(ticket.getStatus()))
                .count();
    }

    public long countScheduledMaintenanceTickets(List<MaintenanceTicket> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            return 0;
        }

        return tickets.stream()
                .filter(ticket -> ticket.getScheduledDate() != null)
                .count();
    }
}