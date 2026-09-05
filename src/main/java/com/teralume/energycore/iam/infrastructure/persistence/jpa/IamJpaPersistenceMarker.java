package com.teralume.energycore.iam.infrastructure.persistence.jpa;

/**
 * Marker class for IAM JPA persistence infrastructure.
 *
 * Current project uses Spring Data repositories directly in the domain/repositories package.
 * This package is reserved for future persistence adapters if stricter DDD separation is required.
 */
public final class IamJpaPersistenceMarker {

    private IamJpaPersistenceMarker() {
    }
}