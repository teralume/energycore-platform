package com.teralume.energycore.servicemanagement.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.servicemanagement.domain.model.aggregates.SupportTicket;
import com.teralume.energycore.servicemanagement.domain.repositories.SupportTicketRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSupportTicketRepository extends JpaRepository<SupportTicket, Long>, SupportTicketRepository {
}
