package com.teralume.energycore.notifications.infrastructure.delivery;

import com.teralume.energycore.notifications.domain.model.aggregates.Alert;
import com.teralume.energycore.notifications.domain.model.aggregates.NotificationPreference;
import com.teralume.energycore.notifications.domain.model.results.NotificationDeliveryDecision;

public interface NotificationDeliveryPort {

    void send(Alert alert, NotificationPreference preference, NotificationDeliveryDecision decision);
}
