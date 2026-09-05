package com.teralume.energycore.shared.interfaces.rest.resources;

import java.time.LocalDateTime;

public record ErrorResource(
        String code,
        String message,
        LocalDateTime timestamp
) {
}