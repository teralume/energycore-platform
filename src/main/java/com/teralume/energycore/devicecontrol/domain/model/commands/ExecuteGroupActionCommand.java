package com.teralume.energycore.devicecontrol.domain.model.commands;

import com.teralume.energycore.devicecontrol.domain.model.DeviceStatus;

public record ExecuteGroupActionCommand(DeviceStatus status) {
}