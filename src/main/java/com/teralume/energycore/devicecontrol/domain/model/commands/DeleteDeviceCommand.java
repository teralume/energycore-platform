package com.teralume.energycore.devicecontrol.domain.model.commands;

public record DeleteDeviceCommand(
        Long userId,
        Long deviceId
) {
}