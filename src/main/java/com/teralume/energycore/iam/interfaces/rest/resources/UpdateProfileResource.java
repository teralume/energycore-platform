package com.teralume.energycore.iam.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateProfileResource(
        @NotBlank String fullName,
        @Email @NotBlank String email
) {
}