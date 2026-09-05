package com.teralume.energycore.servicemanagement.domain.repositories;

import com.teralume.energycore.servicemanagement.domain.model.aggregates.MaintenanceTicket;

import java.util.List;
import java.util.Optional;

public interface MaintenanceTicketRepository {
    List<MaintenanceTicket> findByUserId(Long userId);

    Optional<MaintenanceTicket> findById(Long id);

    MaintenanceTicket save(MaintenanceTicket maintenanceTicket);

    void delete(MaintenanceTicket maintenanceTicket);
}
