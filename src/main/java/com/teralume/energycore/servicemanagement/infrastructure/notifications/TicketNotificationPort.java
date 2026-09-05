package com.teralume.energycore.servicemanagement.infrastructure.notifications;

public interface TicketNotificationPort {

    void notifyTicketCreated(Long userId, Long ticketId, String ticketType);

    void notifyTicketStatusChanged(Long userId, Long ticketId, String status);
}