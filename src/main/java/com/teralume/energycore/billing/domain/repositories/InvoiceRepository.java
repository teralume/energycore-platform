package com.teralume.energycore.billing.domain.repositories;

import com.teralume.energycore.billing.domain.model.aggregates.Invoice;

import java.util.List;

public interface InvoiceRepository {
    List<Invoice> findByUserId(Long userId);

    Invoice save(Invoice invoice);
}
