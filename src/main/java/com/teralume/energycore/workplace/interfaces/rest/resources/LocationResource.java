package com.teralume.energycore.workplace.interfaces.rest.resources;

import com.teralume.energycore.workplace.domain.model.aggregates.Location;

import java.time.LocalDate;

public record LocationResource(
        Long id,
        Long userId,
        String name,
        String address,
        String type,
        LocalDate createdAt
) {
    public static LocationResource from(Location location) {
        return new LocationResource(
                location.getId(),
                location.getUserId(),
                location.getName(),
                location.getAddress(),
                location.getType(),
                location.getCreatedAt() != null ? location.getCreatedAt().toLocalDate() : null
        );
    }
}
