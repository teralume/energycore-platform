package com.teralume.energycore.devicecontrol.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record PairDeviceResource(
        @NotBlank String pairingCode,
        String alias,
        String room
) {
}
