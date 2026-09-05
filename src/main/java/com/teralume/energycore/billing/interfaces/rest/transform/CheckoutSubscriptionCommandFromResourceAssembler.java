package com.teralume.energycore.billing.interfaces.rest.transform;

import com.teralume.energycore.billing.domain.model.commands.CheckoutSubscriptionCommand;
import com.teralume.energycore.billing.interfaces.rest.resources.CheckoutSubscriptionResource;

public class CheckoutSubscriptionCommandFromResourceAssembler {
    private CheckoutSubscriptionCommandFromResourceAssembler() {
    }

    public static CheckoutSubscriptionCommand toCommandFromResource(CheckoutSubscriptionResource resource, Long userId) {
        return new CheckoutSubscriptionCommand(
                userId,
                resource.planCode(),
                resource.holderName(),
                resource.cardNumber(),
                resource.expirationDate(),
                resource.cvv()
        );
    }
}
