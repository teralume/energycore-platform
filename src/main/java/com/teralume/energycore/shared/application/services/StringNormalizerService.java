package com.teralume.energycore.shared.application.services;

import org.springframework.stereotype.Service;

@Service
public class StringNormalizerService {

    public String normalizeText(String value) {
        if (value == null) return null;
        return value.trim();
    }

    public String normalizeEmail(String email) {
        if (email == null) return null;
        return email.trim().toLowerCase();
    }
}