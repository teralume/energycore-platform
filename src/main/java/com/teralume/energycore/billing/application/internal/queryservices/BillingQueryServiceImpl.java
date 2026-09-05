package com.teralume.energycore.billing.application.internal.queryservices;

import com.teralume.energycore.billing.application.queryservices.BillingQueryService;
import com.teralume.energycore.billing.domain.model.aggregates.Invoice;
import com.teralume.energycore.billing.domain.model.aggregates.Payment;
import com.teralume.energycore.billing.domain.model.aggregates.Plan;
import com.teralume.energycore.billing.domain.model.aggregates.Subscription;
import com.teralume.energycore.billing.domain.model.SubscriptionStatus;
import com.teralume.energycore.billing.domain.model.queries.GetAllPlansQuery;
import com.teralume.energycore.billing.domain.model.queries.GetCurrentSubscriptionQuery;
import com.teralume.energycore.billing.domain.model.queries.GetInvoicesByUserQuery;
import com.teralume.energycore.billing.domain.model.queries.GetPaymentsByUserQuery;
import com.teralume.energycore.billing.domain.repositories.InvoiceRepository;
import com.teralume.energycore.billing.domain.repositories.PaymentRepository;
import com.teralume.energycore.billing.domain.repositories.PlanRepository;
import com.teralume.energycore.billing.domain.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillingQueryServiceImpl implements BillingQueryService {

    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Plan> handle(GetAllPlansQuery query) {
        return planRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Subscription> handle(GetCurrentSubscriptionQuery query) {
        return subscriptionRepository.findFirstByUserIdAndStatus(query.userId(), SubscriptionStatus.ACTIVE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> handle(GetPaymentsByUserQuery query) {
        return paymentRepository.findByUserId(query.userId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> handle(GetInvoicesByUserQuery query) {
        return invoiceRepository.findByUserId(query.userId());
    }
}
