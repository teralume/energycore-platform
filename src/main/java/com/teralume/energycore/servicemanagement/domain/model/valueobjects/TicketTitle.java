package com.teralume.energycore.servicemanagement.domain.model.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.NonBlankText;

public record TicketTitle(String value) {
    public TicketTitle {
        value = NonBlankText.of(value).value();
        if (value.length() > 160) {
            throw new IllegalArgumentException("Ticket title cannot exceed 160 characters.");
        }
    }
}