package com.teralume.energycore.billing.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.billing.domain.model.aggregates.Invoice;
import com.teralume.energycore.billing.domain.repositories.InvoiceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaInvoiceRepository extends JpaRepository<Invoice, Long>, InvoiceRepository {
}
