package com.teralume.energycore.servicemanagement.domain.specifications;

public class OpenTicketSpecification {
    public boolean isSatisfiedBy(String status) {
        return status != null
                && !"CLOSED".equalsIgnoreCase(status)
                && !"COMPLETED".equalsIgnoreCase(status)
                && !"CANCELED".equalsIgnoreCase(status);
    }
}