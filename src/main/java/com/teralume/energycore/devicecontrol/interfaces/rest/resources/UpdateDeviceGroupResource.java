package com.teralume.energycore.devicecontrol.interfaces.rest.resources;

import java.util.List;

public record UpdateDeviceGroupResource(
        String name,
        String description,
        List<Long> deviceIds
) {
}