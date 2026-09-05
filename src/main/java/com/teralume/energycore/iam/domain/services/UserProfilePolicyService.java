package com.teralume.energycore.iam.domain.services;

import org.springframework.stereotype.Service;

@Service
public class UserProfilePolicyService {

    public void validateFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name is required.");
        }

        if (fullName.trim().length() < 3) {
            throw new IllegalArgumentException("Full name must contain at least 3 characters.");
        }
    }

    public void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email format is invalid.");
        }
    }
}