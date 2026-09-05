package com.teralume.energycore.workplace.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRoomResource(
        @NotNull Long locationId,
        @NotBlank String name,
        String floor
) {
}