package com.teralume.energycore.devicecontrol.domain.services;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Set;

@Service
public class RoutinePolicyService {

    private static final Set<String> ALLOWED_ACTIONS = Set.of("TURN_ON", "TURN_OFF", "TOGGLE");

    public void validateRoutineData(String name, String action, LocalTime executionTime) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Routine name is required.");
        }

        if (action == null || !ALLOWED_ACTIONS.contains(action.toUpperCase())) {
            throw new IllegalArgumentException("Routine action is invalid.");
        }

        if (executionTime == null) {
            throw new IllegalArgumentException("Routine execution time is required.");
        }
    }
}