package com.teralume.energycore.devicecontrol.interfaces.rest.resources;

import com.teralume.energycore.devicecontrol.domain.model.DeviceStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateDeviceStatusResource(
        @NotNull DeviceStatus status
) {
}
