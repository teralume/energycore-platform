package com.teralume.energycore.billing.interfaces.rest.transform;

import com.teralume.energycore.billing.domain.model.commands.ProcessPaymentCommand;
import com.teralume.energycore.billing.interfaces.rest.resources.ProcessPaymentResource;

public class ProcessPaymentCommandFromResourceAssembler {
    private ProcessPaymentCommandFromResourceAssembler() {
    }

    public static ProcessPaymentCommand toCommandFromResource(ProcessPaymentResource resource, Long userId) {
        return new ProcessPaymentCommand(userId, resource.subscriptionId());
    }
}
