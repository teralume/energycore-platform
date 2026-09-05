package com.teralume.energycore.servicemanagement.domain.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MaintenanceSchedulePolicyService {

    public void validateScheduleDate(LocalDate scheduledDate) {
        if (scheduledDate == null) {
            throw new IllegalArgumentException("Scheduled date is required.");
        }

        if (scheduledDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Scheduled date cannot be in the past.");
        }
    }

    public boolean isScheduledForToday(LocalDate scheduledDate) {
        return scheduledDate != null && scheduledDate.isEqual(LocalDate.now());
    }

    public boolean isOverdue(LocalDate scheduledDate, String status) {
        return scheduledDate != null
                && scheduledDate.isBefore(LocalDate.now())
                && !"CLOSED".equalsIgnoreCase(status)
                && !"CANCELLED".equalsIgnoreCase(status);
    }
}