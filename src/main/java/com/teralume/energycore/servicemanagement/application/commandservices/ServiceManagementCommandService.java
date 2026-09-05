package com.teralume.energycore.servicemanagement.application.commandservices;

import com.teralume.energycore.servicemanagement.domain.model.aggregates.MaintenanceTicket;
import com.teralume.energycore.servicemanagement.domain.model.aggregates.SupportTicket;
import com.teralume.energycore.servicemanagement.domain.model.commands.CreateMaintenanceTicketCommand;
import com.teralume.energycore.servicemanagement.domain.model.commands.CreateSupportTicketCommand;
import com.teralume.energycore.servicemanagement.domain.model.commands.DeleteMaintenanceTicketCommand;
import com.teralume.energycore.servicemanagement.domain.model.commands.DeleteSupportTicketCommand;
import com.teralume.energycore.servicemanagement.domain.model.commands.UpdateTicketStatusCommand;

public interface ServiceManagementCommandService {
    SupportTicket createSupportTicket(CreateSupportTicketCommand command);

    SupportTicket updateSupportStatus(Long userId, Long ticketId, UpdateTicketStatusCommand command);

    void deleteSupportTicket(DeleteSupportTicketCommand command);

    MaintenanceTicket createMaintenanceTicket(CreateMaintenanceTicketCommand command);

    MaintenanceTicket updateMaintenanceStatus(Long userId, Long ticketId, UpdateTicketStatusCommand command);

    void deleteMaintenanceTicket(DeleteMaintenanceTicketCommand command);
}
