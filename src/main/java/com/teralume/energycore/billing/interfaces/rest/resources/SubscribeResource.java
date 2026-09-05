package com.teralume.energycore.billing.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record SubscribeResource(
        @NotBlank String planCode
) {
}
