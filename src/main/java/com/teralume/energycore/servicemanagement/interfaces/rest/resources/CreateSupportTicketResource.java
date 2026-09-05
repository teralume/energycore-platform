package com.teralume.energycore.servicemanagement.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSupportTicketResource(
        @NotBlank String subject,
        @NotBlank String description,
        @NotBlank String priority
) {
}
