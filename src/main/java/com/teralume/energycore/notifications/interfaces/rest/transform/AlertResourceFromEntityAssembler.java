package com.teralume.energycore.notifications.interfaces.rest.transform;

import com.teralume.energycore.notifications.domain.model.aggregates.Alert;
import com.teralume.energycore.notifications.interfaces.rest.resources.AlertResource;

public class AlertResourceFromEntityAssembler {
    public static AlertResource toResourceFromEntity(Alert alert) {
        return AlertResource.from(alert);
    }
}
