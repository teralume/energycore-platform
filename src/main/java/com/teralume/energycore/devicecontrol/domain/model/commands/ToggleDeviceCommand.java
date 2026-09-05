package com.teralume.energycore.devicecontrol.domain.model.commands;

public record ToggleDeviceCommand(
        Long userId,
        Long deviceId
) {
}