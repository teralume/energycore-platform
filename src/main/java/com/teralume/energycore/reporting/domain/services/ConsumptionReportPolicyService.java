package com.teralume.energycore.reporting.domain.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ConsumptionReportPolicyService {

    public void validateReportRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Report start date is required.");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("Report end date is required.");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Report start date cannot be after end date.");
        }
    }

    public boolean isValidRange(LocalDate startDate, LocalDate endDate) {
        return startDate != null && endDate != null && !startDate.isAfter(endDate);
    }
}