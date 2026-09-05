package com.teralume.energycore.devicecontrol.domain.model.commands;

public record AddDeviceToGroupCommand(
        Long userId,
        Long deviceGroupId,
        Long deviceId
) {
}