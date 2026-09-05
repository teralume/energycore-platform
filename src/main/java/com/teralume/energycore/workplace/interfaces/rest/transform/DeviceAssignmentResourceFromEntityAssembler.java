package com.teralume.energycore.workplace.interfaces.rest.transform;

import com.teralume.energycore.workplace.domain.model.aggregates.DeviceAssignment;
import com.teralume.energycore.workplace.interfaces.rest.resources.DeviceAssignmentResource;

public class DeviceAssignmentResourceFromEntityAssembler {
    private DeviceAssignmentResourceFromEntityAssembler() {
    }

    public static DeviceAssignmentResource toResourceFromEntity(DeviceAssignment entity) {
        return DeviceAssignmentResource.from(entity);
    }
}