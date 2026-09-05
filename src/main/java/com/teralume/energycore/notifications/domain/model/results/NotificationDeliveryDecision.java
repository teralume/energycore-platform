package com.teralume.energycore.notifications.domain.model.results;

import com.teralume.energycore.notifications.domain.model.valueobjects.NotificationDeliveryMode;

public record NotificationDeliveryDecision(
        boolean allowed,
        NotificationDeliveryMode deliveryMode,
        boolean soundAllowed,
        boolean toastAllowed,
        boolean inboxAllowed,
        String reason
) {
}
