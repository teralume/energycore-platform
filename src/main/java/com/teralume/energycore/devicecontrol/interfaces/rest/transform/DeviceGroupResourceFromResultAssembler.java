package com.teralume.energycore.devicecontrol.interfaces.rest.transform;

import com.teralume.energycore.devicecontrol.application.results.DeviceGroupDetails;
import com.teralume.energycore.devicecontrol.interfaces.rest.resources.DeviceGroupResource;

import java.time.LocalDate;
import java.util.List;

public class DeviceGroupResourceFromResultAssembler {
    public static DeviceGroupResource toResourceFromResult(DeviceGroupDetails details) {
        var group = details.group();
        return new DeviceGroupResource(
                group.getId(),
                group.getUserId(),
                group.getName(),
                group.getDescription(),
                details.deviceIds() != null ? details.deviceIds() : List.of(),
                group.getCreatedAt() != null ? group.getCreatedAt().toLocalDate() : LocalDate.now()
        );
    }
}