package com.teralume.energycore.servicemanagement.domain.services;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TicketPriorityPolicyService {

    private static final Set<String> ALLOWED_PRIORITIES = Set.of("LOW", "MEDIUM", "HIGH", "URGENT");

    public boolean isValidPriority(String priority) {
        return priority != null && ALLOWED_PRIORITIES.contains(priority.toUpperCase());
    }
}