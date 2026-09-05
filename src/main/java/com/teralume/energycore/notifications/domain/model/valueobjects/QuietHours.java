package com.teralume.energycore.notifications.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.time.LocalTime;

@Embeddable
public class QuietHours {
    private Boolean enabled = false;
    private String startTime = "22:00";
    private String endTime = "07:00";

    public QuietHours() {
    }

    public QuietHours(Boolean enabled, String startTime, String endTime) {
        this.enabled = enabled != null && enabled;
        this.startTime = normalizeTime(startTime, "22:00");
        this.endTime = normalizeTime(endTime, "07:00");
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public boolean isNowQuiet(LocalTime now) {
        if (!Boolean.TRUE.equals(enabled)) {
            return false;
        }

        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);

        if (start.equals(end)) {
            return true;
        }

        if (start.isBefore(end)) {
            return !now.isBefore(start) && now.isBefore(end);
        }

        return !now.isBefore(start) || now.isBefore(end);
    }

    private String normalizeTime(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }

        return LocalTime.parse(value.trim()).toString();
    }
}
