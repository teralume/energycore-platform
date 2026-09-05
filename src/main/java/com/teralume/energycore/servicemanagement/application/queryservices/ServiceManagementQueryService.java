package com.teralume.energycore.servicemanagement.application.queryservices;

import com.teralume.energycore.servicemanagement.domain.model.aggregates.MaintenanceTicket;
import com.teralume.energycore.servicemanagement.domain.model.aggregates.SupportTicket;

import java.util.List;

public interface ServiceManagementQueryService {
    List<SupportTicket> getSupportTickets(Long userId);

    List<MaintenanceTicket> getMaintenanceTickets(Long userId);
}
