package com.teralume.energycore.workplace.domain.services;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LocationPolicyService {

    private static final Set<String> ALLOWED_LOCATION_TYPES = Set.of(
            "HOME",
            "OFFICE",
            "STORE",
            "WAREHOUSE",
            "OTHER"
    );

    public void validateLocation(String name, String type) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Location name is required.");
        }

        if (name.trim().length() < 2) {
            throw new IllegalArgumentException("Location name must contain at least 2 characters.");
        }

        if (type != null && !type.isBlank() && !ALLOWED_LOCATION_TYPES.contains(type.toUpperCase())) {
            throw new IllegalArgumentException("Location type is invalid.");
        }
    }

    public String normalizeType(String type) {
        if (type == null || type.isBlank()) {
            return "OTHER";
        }

        return type.trim().toUpperCase();
    }
}