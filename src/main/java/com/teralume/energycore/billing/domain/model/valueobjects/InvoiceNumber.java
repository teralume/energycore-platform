package com.teralume.energycore.billing.domain.model.valueobjects;

import java.util.regex.Pattern;

public record InvoiceNumber(String value) {
    private static final Pattern PATTERN = Pattern.compile("^INV-[0-9]{6,}$");

    public InvoiceNumber {
        if (value == null || !PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invoice number must match INV-000000 format.");
        }
    }
}