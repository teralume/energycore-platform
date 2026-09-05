package com.teralume.energycore.shared.domain.model;

public record EntityId(Long value) {

    public EntityId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Entity id must be positive.");
        }
    }
}