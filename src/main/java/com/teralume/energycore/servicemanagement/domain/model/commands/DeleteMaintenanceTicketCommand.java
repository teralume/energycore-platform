package com.teralume.energycore.servicemanagement.domain.model.commands;

public record DeleteMaintenanceTicketCommand(
        Long userId,
        Long ticketId
) {
}