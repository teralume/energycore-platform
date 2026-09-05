package com.teralume.energycore.shared.domain.valueobjects;

import java.time.LocalDate;

public record DateRange(LocalDate startDate, LocalDate endDate) {
    public DateRange {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Date range requires start and end dates.");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Date range end date cannot be before start date.");
        }
    }

    public boolean contains(LocalDate date) {
        return date != null && !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}