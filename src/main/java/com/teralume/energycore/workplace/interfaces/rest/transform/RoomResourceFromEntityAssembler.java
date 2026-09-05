package com.teralume.energycore.workplace.interfaces.rest.transform;

import com.teralume.energycore.workplace.domain.model.aggregates.Room;
import com.teralume.energycore.workplace.interfaces.rest.resources.RoomResource;

public class RoomResourceFromEntityAssembler {
    private RoomResourceFromEntityAssembler() {
    }

    public static RoomResource toResourceFromEntity(Room entity) {
        return RoomResource.from(entity);
    }
}