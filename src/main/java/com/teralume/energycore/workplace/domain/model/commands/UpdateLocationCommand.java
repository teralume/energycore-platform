package com.teralume.energycore.workplace.domain.model.commands;

public record UpdateLocationCommand(
        Long userId,
        Long locationId,
        String name,
        String address,
        String type
) {
}