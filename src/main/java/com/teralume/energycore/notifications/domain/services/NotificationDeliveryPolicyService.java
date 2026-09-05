package com.teralume.energycore.notifications.domain.services;

import com.teralume.energycore.notifications.domain.model.aggregates.Alert;
import com.teralume.energycore.notifications.domain.model.aggregates.NotificationPreference;
import com.teralume.energycore.notifications.domain.model.results.NotificationDeliveryDecision;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertLevel;
import com.teralume.energycore.notifications.domain.model.valueobjects.NotificationDeliveryMode;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationDeliveryPolicyService {

    public NotificationDeliveryDecision decide(NotificationPreference preference, Alert alert, LocalTime now) {
        if (preference == null) {
            return new NotificationDeliveryDecision(true, NotificationDeliveryMode.BANNER, true, true, true, "Sin preferencias especificas.");
        }

        if (!isLevelAllowed(preference, alert.getLevel())) {
            return new NotificationDeliveryDecision(false, NotificationDeliveryMode.INBOX_ONLY, false, false, true, "Nivel filtrado por preferencias.");
        }

        if (!isSourceAllowed(preference, alert.getSourceType() == null ? "SYSTEM" : alert.getSourceType().name())) {
            return new NotificationDeliveryDecision(false, NotificationDeliveryMode.INBOX_ONLY, false, false, true, "Origen filtrado por preferencias.");
        }

        boolean quiet = preference.getQuietHours() != null && preference.getQuietHours().isNowQuiet(now);
        boolean critical = AlertLevel.CRITICAL.name().equalsIgnoreCase(alert.getLevel());
        boolean breakQuiet = critical && Boolean.TRUE.equals(preference.getCriticalBreaksQuietHours());
        boolean soundAllowed = !quiet || breakQuiet;
        boolean toastAllowed = Boolean.TRUE.equals(preference.getToastEnabled()) && (!quiet || breakQuiet);
        NotificationDeliveryMode mode = quiet && !breakQuiet
                ? NotificationDeliveryMode.QUIET
                : preference.getDefaultDeliveryMode();

        return new NotificationDeliveryDecision(
                true,
                mode,
                soundAllowed,
                toastAllowed,
                Boolean.TRUE.equals(preference.getInAppEnabled()) || Boolean.TRUE.equals(preference.getDashboardEnabled()),
                quiet && !breakQuiet ? "Horario silencioso: se conserva en bandeja sin ruido invasivo." : "Permitida por preferencias."
        );
    }

    private boolean isLevelAllowed(NotificationPreference preference, String level) {
        if (Boolean.TRUE.equals(preference.getCriticalOnly())) {
            return AlertLevel.CRITICAL.name().equalsIgnoreCase(level);
        }

        return split(preference.getAllowedLevels()).contains(level.toUpperCase());
    }

    private boolean isSourceAllowed(NotificationPreference preference, String sourceType) {
        return split(preference.getAllowedSourceTypes()).contains(sourceType.toUpperCase());
    }

    private Set<String> split(String value) {
        if (value == null || value.isBlank()) {
            return Set.of();
        }

        return Arrays.stream(value.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .filter(item -> !item.isBlank())
                .collect(Collectors.toSet());
    }
}
