package com.teralume.energycore.servicemanagement.domain.model.valueobjects;

import java.util.regex.Pattern;

public record TicketCode(String value) {
    private static final Pattern PATTERN = Pattern.compile("^(SUP|MNT)-[0-9]{6,}$");

    public TicketCode {
        if (value == null || !PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Ticket code format is invalid.");
        }
    }
}