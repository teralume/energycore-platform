package com.teralume.energycore.servicemanagement.domain.repositories;

import com.teralume.energycore.servicemanagement.domain.model.aggregates.SupportTicket;

import java.util.List;
import java.util.Optional;

public interface SupportTicketRepository {
    List<SupportTicket> findByUserId(Long userId);

    Optional<SupportTicket> findById(Long id);

    SupportTicket save(SupportTicket supportTicket);

    void delete(SupportTicket supportTicket);
}
