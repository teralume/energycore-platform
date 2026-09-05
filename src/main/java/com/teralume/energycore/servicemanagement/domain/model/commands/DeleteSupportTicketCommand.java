package com.teralume.energycore.servicemanagement.domain.model.commands;

public record DeleteSupportTicketCommand(
        Long userId,
        Long ticketId
) {
}