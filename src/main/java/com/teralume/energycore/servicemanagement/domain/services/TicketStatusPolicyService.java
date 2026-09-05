package com.teralume.energycore.servicemanagement.domain.services;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TicketStatusPolicyService {

    private static final Set<String> ALLOWED_STATUSES = Set.of("OPEN", "IN_PROGRESS", "CLOSED", "CANCELLED");

    public boolean isValidStatus(String status) {
        return status != null && ALLOWED_STATUSES.contains(status.toUpperCase());
    }
}