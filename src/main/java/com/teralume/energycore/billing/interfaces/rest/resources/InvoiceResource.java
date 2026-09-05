package com.teralume.energycore.billing.interfaces.rest.resources;

import com.teralume.energycore.billing.domain.model.aggregates.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InvoiceResource(
        Long id,
        Long userId,
        String invoiceNumber,
        BigDecimal totalAmount,
        String currency,
        LocalDate issuedAt
) {
    public static InvoiceResource from(Invoice invoice) {
        return new InvoiceResource(
                invoice.getId(),
                invoice.getUserId(),
                invoice.getInvoiceNumber(),
                invoice.getTotalAmount() != null ? invoice.getTotalAmount().getAmount() : null,
                invoice.getTotalAmount() != null ? invoice.getTotalAmount().getCurrency() : null,
                invoice.getIssuedAt()
        );
    }
}