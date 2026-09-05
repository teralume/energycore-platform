package com.teralume.energycore.billing.domain.model.valueobjects;

public record SubscriptionId(Long value) {
    public SubscriptionId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Subscription id must be positive.");
        }
    }
}