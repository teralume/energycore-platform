package com.teralume.energycore.devicecontrol.interfaces.rest.resources;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.DeviceGroup;

import java.time.LocalDate;
import java.util.List;

public record DeviceGroupResource(
        Long id,
        Long userId,
        String name,
        String description,
        List<Long> deviceIds,
        LocalDate createdAt
) {
    public static DeviceGroupResource from(DeviceGroup group) {
        return from(group, List.of());
    }

    public static DeviceGroupResource from(DeviceGroup group, List<Long> deviceIds) {
        return new DeviceGroupResource(
                group.getId(),
                group.getUserId(),
                group.getName(),
                group.getDescription(),
                deviceIds != null ? deviceIds : List.of(),
                group.getCreatedAt() != null
                        ? group.getCreatedAt().toLocalDate()
                        : LocalDate.now()
        );
    }
}