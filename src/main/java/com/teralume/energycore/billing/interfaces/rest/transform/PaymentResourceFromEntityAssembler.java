package com.teralume.energycore.billing.interfaces.rest.transform;

import com.teralume.energycore.billing.domain.model.aggregates.Payment;
import com.teralume.energycore.billing.interfaces.rest.resources.PaymentResource;

public class PaymentResourceFromEntityAssembler {
    private PaymentResourceFromEntityAssembler() {
    }

    public static PaymentResource toResourceFromEntity(Payment entity) {
        return PaymentResource.from(entity);
    }
}