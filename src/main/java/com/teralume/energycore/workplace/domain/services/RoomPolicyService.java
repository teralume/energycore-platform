package com.teralume.energycore.workplace.domain.services;

import org.springframework.stereotype.Service;

@Service
public class RoomPolicyService {

    public void validateRoom(Long locationId, String name) {
        if (locationId == null) {
            throw new IllegalArgumentException("Location id is required.");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Room name is required.");
        }

        if (name.trim().length() < 2) {
            throw new IllegalArgumentException("Room name must contain at least 2 characters.");
        }
    }

    public String normalizeFloor(String floor) {
        if (floor == null || floor.isBlank()) {
            return "N/A";
        }

        return floor.trim();
    }
}