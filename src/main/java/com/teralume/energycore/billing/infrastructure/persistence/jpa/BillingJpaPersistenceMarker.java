package com.teralume.energycore.billing.infrastructure.persistence.jpa;

/**
 * Marker class for Billing JPA persistence infrastructure.
 *
 * The current implementation uses Spring Data repositories directly
 * in billing/domain/repositories.
 *
 * This package is reserved for future persistence adapters, mappers,
 * specifications, or custom JPA implementations.
 */
public final class BillingJpaPersistenceMarker {

    private BillingJpaPersistenceMarker() {
    }
}