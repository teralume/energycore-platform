package com.teralume.energycore.billing.application.commandservices;

import com.teralume.energycore.billing.domain.model.aggregates.Payment;
import com.teralume.energycore.billing.domain.model.aggregates.Subscription;
import com.teralume.energycore.billing.domain.model.commands.CancelSubscriptionCommand;
import com.teralume.energycore.billing.domain.model.commands.CheckoutSubscriptionCommand;
import com.teralume.energycore.billing.domain.model.commands.ProcessPaymentCommand;

public interface BillingCommandService {
    Subscription handle(CheckoutSubscriptionCommand command);
    void handle(CancelSubscriptionCommand command);
    Payment handle(ProcessPaymentCommand command);
}
