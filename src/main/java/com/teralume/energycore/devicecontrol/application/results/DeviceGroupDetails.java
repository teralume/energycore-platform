package com.teralume.energycore.devicecontrol.application.results;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.DeviceGroup;

import java.util.List;

public record DeviceGroupDetails(DeviceGroup group, List<Long> deviceIds) {
}