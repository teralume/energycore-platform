package com.teralume.energycore.notifications.domain.services;

import com.teralume.energycore.devicecontrol.domain.repositories.DeviceGroupRepository;
import com.teralume.energycore.devicecontrol.domain.repositories.DeviceRepository;
import com.teralume.energycore.devicecontrol.domain.repositories.RoutineRepository;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertSourceType;
import com.teralume.energycore.reporting.domain.repositories.EnergyGoalRepository;
import com.teralume.energycore.workplace.domain.model.aggregates.Room;
import com.teralume.energycore.workplace.domain.repositories.LocationRepository;
import com.teralume.energycore.workplace.domain.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertSourceOwnershipService {

    private final DeviceRepository deviceRepository;
    private final DeviceGroupRepository deviceGroupRepository;
    private final RoutineRepository routineRepository;
    private final LocationRepository locationRepository;
    private final RoomRepository roomRepository;
    private final EnergyGoalRepository energyGoalRepository;

    public void validateSourceOwnership(Long userId, AlertSourceType sourceType, String sourceId) {
        if (sourceType == null || sourceType == AlertSourceType.SYSTEM || sourceType == AlertSourceType.RULE
                || sourceType == AlertSourceType.REPORT || sourceType == AlertSourceType.MODE) {
            return;
        }

        if (sourceId == null || sourceId.isBlank()) {
            return;
        }

        Long id = parseId(sourceId);

        switch (sourceType) {
            case DEVICE -> deviceRepository.findByIdAndUserId(id, userId)
                    .orElseThrow(() -> new IllegalArgumentException("Alert source device was not found."));
            case GROUP -> deviceGroupRepository.findById(id)
                    .filter(group -> group.getUserId().equals(userId))
                    .orElseThrow(() -> new IllegalArgumentException("Alert source group was not found."));
            case ROUTINE -> routineRepository.findByIdAndUserId(id, userId)
                    .orElseThrow(() -> new IllegalArgumentException("Alert source routine was not found."));
            case WORKPLACE -> locationRepository.findById(id)
                    .filter(location -> location.getUserId().equals(userId))
                    .orElseThrow(() -> new IllegalArgumentException("Alert source workplace was not found."));
            case ROOM -> {
                Room room = roomRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Alert source room was not found."));
                locationRepository.findById(room.getLocationId())
                        .filter(location -> location.getUserId().equals(userId))
                        .orElseThrow(() -> new IllegalArgumentException("Alert source room does not belong to user."));
            }
            case GOAL -> energyGoalRepository.findById(id)
                    .filter(goal -> goal.getUserId().equals(userId))
                    .orElseThrow(() -> new IllegalArgumentException("Alert source goal was not found."));
            default -> {
            }
        }
    }

    private Long parseId(String sourceId) {
        try {
            return Long.parseLong(sourceId.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Alert source id must be numeric for this source type.");
        }
    }
}
