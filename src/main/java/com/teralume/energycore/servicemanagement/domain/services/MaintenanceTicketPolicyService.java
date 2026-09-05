package com.teralume.energycore.servicemanagement.domain.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class MaintenanceTicketPolicyService {

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "INSPECTION",
            "REPAIR",
            "REPLACEMENT",
            "CALIBRATION",
            "OTHER"
    );

    public void validateMaintenanceTicket(
            Long deviceId,
            String deviceName,
            String type,
            String description,
            LocalDate scheduledDate
    ) {
        if (deviceId == null) {
            throw new IllegalArgumentException("Device id is required.");
        }

        if (deviceName == null || deviceName.isBlank()) {
            throw new IllegalArgumentException("Device name is required.");
        }

        if (type == null || !ALLOWED_TYPES.contains(type.toUpperCase())) {
            throw new IllegalArgumentException("Maintenance type is invalid.");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Maintenance description is required.");
        }

        if (scheduledDate != null && scheduledDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Scheduled date cannot be in the past.");
        }
    }

    public String normalizeType(String type) {
        if (type == null || type.isBlank()) {
            return "OTHER";
        }

        return type.trim().toUpperCase();
    }
}