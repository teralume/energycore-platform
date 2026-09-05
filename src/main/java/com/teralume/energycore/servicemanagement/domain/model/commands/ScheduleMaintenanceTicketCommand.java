package com.teralume.energycore.servicemanagement.domain.model.commands;

import java.time.LocalDate;

public record ScheduleMaintenanceTicketCommand(
        Long userId,
        Long ticketId,
        LocalDate scheduledDate
) {
}