package com.teralume.energycore.energymonitoring.domain.model.valueobjects;

public record SampleSeconds(Integer value) {
    public SampleSeconds {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("Sample seconds cannot be negative.");
        }
        if (value > 3600) {
            throw new IllegalArgumentException("Sample seconds cannot exceed one hour.");
        }
    }
}