package com.teralume.energycore.energymonitoring.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateEnergyReadingResource(
        @NotNull Long deviceId,
        @NotBlank String deviceName,
        @NotNull BigDecimal watts
) {
}
