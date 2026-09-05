package com.teralume.energycore.notifications.domain.services;

import com.teralume.energycore.notifications.domain.model.aggregates.Alert;
import com.teralume.energycore.notifications.domain.model.aggregates.NotificationPreference;
import org.springframework.stereotype.Service;

@Service
public class NotificationChannelPolicyService {

    private final NotificationDeliveryPolicyService deliveryPolicyService;

    public NotificationChannelPolicyService(NotificationDeliveryPolicyService deliveryPolicyService) {
        this.deliveryPolicyService = deliveryPolicyService;
    }

    public boolean canSendEmail(NotificationPreference preference, Alert alert) {
        if (preference == null || alert == null) return false;
        if (!Boolean.TRUE.equals(preference.getEmailEnabled())) return false;
        return allowsByLevel(preference, alert);
    }

    public boolean canSendPush(NotificationPreference preference, Alert alert) {
        if (preference == null || alert == null) return false;
        if (!Boolean.TRUE.equals(preference.getPushEnabled())) return false;
        return allowsByLevel(preference, alert);
    }

    private boolean allowsByLevel(NotificationPreference preference, Alert alert) {
        return deliveryPolicyService.decide(preference, alert, java.time.LocalTime.now()).allowed();
    }
}
