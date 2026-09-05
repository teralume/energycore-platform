package com.teralume.energycore.workplace.domain.model.commands;

public record AssignDeviceCommand(
        Long deviceId,
        Long roomId,
        Long locationId
) {
}