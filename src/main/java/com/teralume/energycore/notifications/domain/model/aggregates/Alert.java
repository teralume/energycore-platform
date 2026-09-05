package com.teralume.energycore.notifications.domain.model.aggregates;

import com.teralume.energycore.notifications.domain.model.valueobjects.AlertEventType;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertLevel;
import com.teralume.energycore.notifications.domain.model.valueobjects.AlertSourceType;
import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alerts")
public class Alert extends AuditableEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false, length = 30)
    private String level;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private AlertSourceType sourceType = AlertSourceType.SYSTEM;

    @Column(length = 80)
    private String sourceId;

    @Column(length = 180)
    private String sourceLabel;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private AlertEventType eventType = AlertEventType.MANUAL;

    @Column(length = 220)
    private String threadKey;

    @Column(columnDefinition = "TEXT")
    private String evidence;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column(columnDefinition = "TEXT")
    private String recommendedAction;

    @Column
    private Integer severityScore = 25;

    @Column
    private Integer repeatCount = 1;

    @Column
    private Boolean active = true;

    @Column
    private Boolean resolved = false;

    @Column
    private Boolean readStatus = false;

    private LocalDateTime firstDetectedAt;

    private LocalDateTime lastTriggeredAt;

    private LocalDateTime dismissedUntil;

    private LocalDateTime expiresAt;

    public void markAsRead() {
        this.readStatus = true;
    }

    public void refresh(
            String title,
            String message,
            String level,
            String evidence,
            String explanation,
            String recommendedAction,
            Integer severityScore,
            LocalDateTime now
    ) {
        this.title = title;
        this.message = message;
        this.level = level;
        this.evidence = evidence;
        this.explanation = explanation;
        this.recommendedAction = recommendedAction;
        this.severityScore = normalizeScore(severityScore);
        this.lastTriggeredAt = now;
        this.repeatCount = this.repeatCount == null ? 1 : this.repeatCount + 1;
        this.active = true;
        this.resolved = false;
        this.readStatus = false;
    }

    public void dismissUntil(LocalDateTime until) {
        if (until == null || until.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Dismissal time must be in the future.");
        }

        this.dismissedUntil = until;
    }

    public void resolve() {
        this.active = false;
        this.resolved = true;
    }

    public void expire() {
        this.active = false;
    }

    public boolean isExpired() {
        return this.expiresAt != null && this.expiresAt.isBefore(LocalDateTime.now());
    }

    public boolean isSilenced() {
        return this.dismissedUntil != null && this.dismissedUntil.isAfter(LocalDateTime.now());
    }

    public void ensureDetectionDates(LocalDateTime now) {
        if (this.firstDetectedAt == null) {
            this.firstDetectedAt = now;
        }

        if (this.lastTriggeredAt == null) {
            this.lastTriggeredAt = now;
        }
    }

    public void setSeverityScore(Integer severityScore) {
        this.severityScore = normalizeScore(severityScore);
    }

    public boolean isCritical() {
        return AlertLevel.CRITICAL.name().equalsIgnoreCase(this.level);
    }

    private Integer normalizeScore(Integer score) {
        if (score == null) {
            return 25;
        }

        return Math.max(0, Math.min(100, score));
    }
}
