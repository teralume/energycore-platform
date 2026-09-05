package com.teralume.energycore.billing.domain.repositories;

import com.teralume.energycore.billing.domain.model.aggregates.Payment;

import java.util.List;

public interface PaymentRepository {
    List<Payment> findByUserId(Long userId);

    Payment save(Payment payment);
}
