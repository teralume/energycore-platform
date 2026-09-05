package com.teralume.energycore.notifications.interfaces.rest.transform;

import com.teralume.energycore.notifications.domain.model.aggregates.NotificationPreference;
import com.teralume.energycore.notifications.interfaces.rest.resources.NotificationPreferenceResource;

public class NotificationPreferenceResourceFromEntityAssembler {
    public static NotificationPreferenceResource toResourceFromEntity(NotificationPreference preference) {
        return NotificationPreferenceResource.from(preference);
    }
}
