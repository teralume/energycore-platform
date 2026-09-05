package com.teralume.energycore.devicecontrol.interfaces.rest.resources;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.Device;

import java.math.BigDecimal;

public record DeviceResource(
        Long id,
        Long userId,
        String name,
        String room,
        String type,
        BigDecimal powerWatts,
        String status
) {
    public static DeviceResource from(Device device) {
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