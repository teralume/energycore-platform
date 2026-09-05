package com.teralume.energycore.devicecontrol.interfaces.rest.transform;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.Device;
import com.teralume.energycore.devicecontrol.interfaces.rest.resources.DeviceResource;

public class DeviceResourceFromEntityAssembler {
    public static DeviceResource toResourceFromEntity(Device device) {
        return new DeviceResource(
                device.getId(),
                device.getUserId(),
                device.getName(),
                device.getRoom(),
                device.getType(),
                device.getPowerWatts(),
                device.getStatus().name()
        );
    }
}