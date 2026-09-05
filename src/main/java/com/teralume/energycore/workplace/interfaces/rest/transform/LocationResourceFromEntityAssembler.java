package com.teralume.energycore.workplace.interfaces.rest.transform;

import com.teralume.energycore.workplace.domain.model.aggregates.Location;
import com.teralume.energycore.workplace.interfaces.rest.resources.LocationResource;

public class LocationResourceFromEntityAssembler {
    private LocationResourceFromEntityAssembler() {
    }

    public static LocationResource toResourceFromEntity(Location entity) {
        return LocationResource.from(entity);
    }
}