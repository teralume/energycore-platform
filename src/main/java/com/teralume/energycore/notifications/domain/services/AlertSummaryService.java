package com.teralume.energycore.notifications.domain.services;

import com.teralume.energycore.notifications.domain.model.aggregates.Alert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertSummaryService {

    public long countUnread(List<Alert> alerts) {
        if (alerts == null || alerts.isEmpty()) {
            return 0;
        }

        return alerts.stream()
                .filter(alert -> !Boolean.TRUE.equals(alert.getReadStatus()))
                .count();
    }

    public long countCritical(List<Alert> alerts) {
        if (alerts == null || alerts.isEmpty()) {
            return 0;
        }

        return alerts.stream()
                .filter(alert -> "CRITICAL".equalsIgnoreCase(alert.getLevel()))
                .count();
    }
}