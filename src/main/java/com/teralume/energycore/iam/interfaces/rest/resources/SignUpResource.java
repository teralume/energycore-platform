package com.teralume.energycore.iam.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpResource(
        @NotBlank String fullName,
        @Email @NotBlank String email,
        @Size(min = 8) String password
) {
}