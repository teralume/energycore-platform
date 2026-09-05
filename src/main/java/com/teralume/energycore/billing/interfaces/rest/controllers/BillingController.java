package com.teralume.energycore.billing.interfaces.rest.controllers;

import com.teralume.energycore.billing.application.commandservices.BillingCommandService;
import com.teralume.energycore.billing.application.queryservices.BillingQueryService;
import com.teralume.energycore.billing.domain.model.commands.CancelSubscriptionCommand;
import com.teralume.energycore.billing.domain.model.queries.GetAllPlansQuery;
import com.teralume.energycore.billing.domain.model.queries.GetCurrentSubscriptionQuery;
import com.teralume.energycore.billing.domain.model.queries.GetInvoicesByUserQuery;
import com.teralume.energycore.billing.domain.model.queries.GetPaymentsByUserQuery;
import com.teralume.energycore.billing.interfaces.rest.resources.*;
import com.teralume.energycore.billing.interfaces.rest.transform.InvoiceResourceFromEntityAssembler;
import com.teralume.energycore.billing.interfaces.rest.transform.PaymentResourceFromEntityAssembler;
import com.teralume.energycore.billing.interfaces.rest.transform.PlanResourceFromEntityAssembler;
import com.teralume.energycore.billing.interfaces.rest.transform.CheckoutSubscriptionCommandFromResourceAssembler;
import com.teralume.energycore.billing.interfaces.rest.transform.ProcessPaymentCommandFromResourceAssembler;
import com.teralume.energycore.billing.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import com.teralume.energycore.iam.application.security.AccessAuthorizationService;
import com.teralume.energycore.iam.domain.model.AccessPermission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingCommandService billingCommandService;
    private final BillingQueryService billingQueryService;
    private final AccessAuthorizationService accessAuthorizationService;

    @GetMapping("/plans")
    public List<PlanResource> getPlans() {
        return billingQueryService.handle(new GetAllPlansQuery())
                .stream()
                .map(PlanResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @GetMapping("/subscriptions/current")
    public SubscriptionResource getCurrentSubscription() {
        return billingQueryService.handle(new GetCurrentSubscriptionQuery(accessAuthorizationService.requireActiveUser()))
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .orElse(null);
    }

    @PostMapping("/subscriptions")
    public SubscriptionResource subscribe(@Valid @RequestBody SubscribeResource request) {
        requireBillingAccess();
        throw new IllegalArgumentException("Direct subscription creation is no longer supported. Use /api/v1/billing/subscriptions/checkout.");
    }

    @PostMapping("/subscriptions/checkout")
    public SubscriptionResource checkoutSubscription(@Valid @RequestBody CheckoutSubscriptionResource request) {
        Long userId = requireBillingAccess();
        return SubscriptionResourceFromEntityAssembler.toResourceFromEntity(
                billingCommandService.handle(
                        CheckoutSubscriptionCommandFromResourceAssembler.toCommandFromResource(
                                request,
                                userId
                        )
                )
        );
    }

    @DeleteMapping("/subscriptions/current")
    public void cancelSubscription() {
        billingCommandService.handle(new CancelSubscriptionCommand(requireBillingAccess()));
    }

    @PostMapping("/payments")
    public PaymentResource processPayment(@Valid @RequestBody ProcessPaymentResource request) {
        Long userId = requireBillingAccess();
        return PaymentResourceFromEntityAssembler.toResourceFromEntity(
                billingCommandService.handle(
                        ProcessPaymentCommandFromResourceAssembler.toCommandFromResource(
                                request,
                                userId
                        )
                )
        );
    }

    @GetMapping("/payments")
    public List<PaymentResource> getPayments() {
        return billingQueryService.handle(new GetPaymentsByUserQuery(requireBillingAccess()))
                .stream()
                .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @GetMapping("/invoices")
    public List<InvoiceResource> getInvoices() {
        return billingQueryService.handle(new GetInvoicesByUserQuery(requireBillingAccess()))
                .stream()
                .map(InvoiceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    private Long requireBillingAccess() {
        return accessAuthorizationService.requirePermission(AccessPermission.MANAGE_BILLING);
    }
}
