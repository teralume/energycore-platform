package com.teralume.energycore.billing.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.billing.domain.model.aggregates.Payment;
import com.teralume.energycore.billing.domain.repositories.PaymentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPaymentRepository extends JpaRepository<Payment, Long>, PaymentRepository {
}
