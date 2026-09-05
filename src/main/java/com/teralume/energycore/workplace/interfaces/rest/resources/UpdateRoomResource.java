package com.teralume.energycore.workplace.interfaces.rest.resources;

public record UpdateRoomResource(
        Long locationId,
        String name,
        String floor
) {
}