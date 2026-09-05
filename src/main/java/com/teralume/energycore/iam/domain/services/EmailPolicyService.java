package com.teralume.energycore.iam.domain.services;

import org.springframework.stereotype.Service;

@Service
public class EmailPolicyService {

    public boolean isAllowedEmail(String email) {
        if (email == null) return false;

        String normalizedEmail = email.toLowerCase();

        return normalizedEmail.endsWith("@gmail.com")
                || normalizedEmail.endsWith("@outlook.com")
                || normalizedEmail.endsWith("@hotmail.com");
    }
}