package com.teralume.energycore.billing.interfaces.rest.transform;

import com.teralume.energycore.billing.domain.model.aggregates.Invoice;
import com.teralume.energycore.billing.interfaces.rest.resources.InvoiceResource;

public class InvoiceResourceFromEntityAssembler {
    private InvoiceResourceFromEntityAssembler() {
    }

    public static InvoiceResource toResourceFromEntity(Invoice entity) {
        return InvoiceResource.from(entity);
    }
}