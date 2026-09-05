package com.teralume.energycore.devicecontrol.domain.model.commands;

public record DeleteDeviceGroupCommand(
        Long userId,
        Long deviceGroupId
) {
}