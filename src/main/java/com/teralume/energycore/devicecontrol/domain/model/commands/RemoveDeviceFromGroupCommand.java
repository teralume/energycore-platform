package com.teralume.energycore.devicecontrol.domain.model.commands;

public record RemoveDeviceFromGroupCommand(
        Long userId,
        Long deviceGroupId,
        Long deviceId
) {
}