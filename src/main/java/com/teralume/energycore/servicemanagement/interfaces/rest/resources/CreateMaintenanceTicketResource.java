package com.teralume.energycore.servicemanagement.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateMaintenanceTicketResource(
        @NotNull Long deviceId,
        @NotBlank String deviceName,
        @NotBlank String type,
        @NotBlank String description,
        LocalDate scheduledDate
) {
}
