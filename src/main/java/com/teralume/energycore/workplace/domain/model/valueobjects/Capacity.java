package com.teralume.energycore.workplace.domain.model.valueobjects;

public record Capacity(Integer value) {
    public Capacity {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        if (value > 10000) {
            throw new IllegalArgumentException("Capacity exceeds supported range.");
        }
    }
}