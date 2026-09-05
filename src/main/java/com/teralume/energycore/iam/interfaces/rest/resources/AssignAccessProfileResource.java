package com.teralume.energycore.iam.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record AssignAccessProfileResource(
        @NotNull Long accessProfileId
) {
}
