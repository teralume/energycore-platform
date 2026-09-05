package com.teralume.energycore.shared.domain.valueobjects;

public record NonBlankText(String value) {
    public NonBlankText {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Text cannot be blank.");
        }
        value = value.trim();
    }

    public static NonBlankText of(String value) {
        return new NonBlankText(value);
    }
}