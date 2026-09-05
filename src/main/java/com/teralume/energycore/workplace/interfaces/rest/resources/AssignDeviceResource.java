package com.teralume.energycore.workplace.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record AssignDeviceResource(
        @NotNull Long deviceId,
        Long roomId,
        @NotNull Long locationId
) {
}
