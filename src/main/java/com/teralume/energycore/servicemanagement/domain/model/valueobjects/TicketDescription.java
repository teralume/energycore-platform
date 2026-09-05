package com.teralume.energycore.servicemanagement.domain.model.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.NonBlankText;

public record TicketDescription(String value) {
    public TicketDescription {
        value = NonBlankText.of(value).value();
        if (value.length() > 2000) {
            throw new IllegalArgumentException("Ticket description cannot exceed 2000 characters.");
        }
    }
}