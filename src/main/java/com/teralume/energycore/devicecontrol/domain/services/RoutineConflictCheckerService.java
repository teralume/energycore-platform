package com.teralume.energycore.devicecontrol.domain.services;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.Routine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineConflictCheckerService {

    public boolean hasConflict(List<Routine> routines, Routine candidate) {
        if (routines == null || candidate == null) {
            return false;
        }

        return routines.stream()
                .anyMatch(existing ->
                        existing.getTargetType() == candidate.getTargetType()
                                && existing.getTargetId().equals(candidate.getTargetId())
                                && existing.getTime().equals(candidate.getTime())
                                && existing.getAction() == candidate.getAction()
                );
    }
}