package com.teralume.energycore.servicemanagement.domain.model.commands;

public record CreateSupportTicketCommand(
        Long userId,
        String subject,
        String description,
        String priority
) {
}