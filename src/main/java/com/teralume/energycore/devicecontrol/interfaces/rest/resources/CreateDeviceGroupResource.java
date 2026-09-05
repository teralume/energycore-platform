package com.teralume.energycore.devicecontrol.interfaces.rest.resources;

import java.util.List;

public record CreateDeviceGroupResource(
        String name,
        String description,
        List<Long> deviceIds
) {
}
