package com.teralume.energycore.energymonitoring.interfaces.rest.resources;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateEnergySamplingSettingsResource(
        @NotNull
        @Min(5)
        @Max(3600)
        Integer sampleSeconds
) {
}