package com.teralume.energycore.billing.domain.services;

import com.teralume.energycore.billing.domain.model.aggregates.Invoice;
import com.teralume.energycore.billing.domain.model.aggregates.Payment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class InvoiceGenerationService {

    public Invoice generateInvoice(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment is required to generate an invoice.");
        }

        Invoice invoice = new Invoice();
        invoice.setUserId(payment.getUserId());
        invoice.setPayment(payment);
        invoice.setTotalAmount(payment.getAmount());
        invoice.setIssuedAt(LocalDate.now());

        return invoice;
    }

    public String generateInvoiceNumber(Long paymentId) {
        if (paymentId == null) {
            return "INV-PENDING";
        }

        return "INV-" + paymentId;
    }
}