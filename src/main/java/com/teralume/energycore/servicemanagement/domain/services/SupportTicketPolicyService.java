package com.teralume.energycore.servicemanagement.domain.services;

import org.springframework.stereotype.Service;

@Service
public class SupportTicketPolicyService {

    public void validateSupportTicket(String subject, String description) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Support ticket subject is required.");
        }

        if (subject.trim().length() < 3) {
            throw new IllegalArgumentException("Support ticket subject must contain at least 3 characters.");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Support ticket description is required.");
        }

        if (description.trim().length() < 10) {
            throw new IllegalArgumentException("Support ticket description must contain at least 10 characters.");
        }
    }
}