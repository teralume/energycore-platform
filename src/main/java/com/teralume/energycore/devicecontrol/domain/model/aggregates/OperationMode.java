package com.teralume.energycore.devicecontrol.domain.model.aggregates;

import com.teralume.energycore.devicecontrol.domain.model.OperationModeStatus;
import com.teralume.energycore.devicecontrol.domain.model.entities.OperationModeRoutine;
import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operation_modes")
public class OperationMode extends AuditableEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long locationId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 400)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private OperationModeStatus status = OperationModeStatus.DRAFT;

    @Column(length = 800)
    private String roomIds = "";

    @Column(length = 800)
    private String groupIds = "";

    @Column(length = 800)
    private String deviceIds = "";

    @Column(length = 800)
    private String turnOnDeviceIds = "";

    @Column(length = 800)
    private String turnOffDeviceIds = "";

    @Column(length = 800)
    private String keepOnDeviceIds = "";

    @Column(length = 800)
    private String routineIds = "";

    @Column(length = 800)
    private String routinesToEnableIds = "";

    @Column(length = 800)
    private String routinesToDisableIds = "";

    @Column(length = 800)
    private String goalIds = "";

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "operation_mode_internal_routines",
            joinColumns = @JoinColumn(name = "operation_mode_id")
    )
    @OrderColumn(name = "sort_order")
    private List<OperationModeRoutine> internalRoutines = new ArrayList<>();

    @Column(nullable = false)
    private Boolean allDay = true;

    @Column(length = 10)
    private String startsAt = "00:00";

    @Column(length = 10)
    private String endsAt = "23:59";

    @Column
    private Long ruleProfileId;

    @Column
    private Long preferenceId;

    @Column(nullable = false)
    private Boolean applyRuleProfile = true;

    @Column(nullable = false)
    private Boolean applyNotificationPreference = true;

    @Column(nullable = false)
    private Boolean applyRoutines = true;

    @Column(nullable = false)
    private Boolean preserveCriticalSound = true;

    @Column
    private LocalDateTime lastActivatedAt;

    public void activate(LocalDateTime activatedAt) {
        this.status = OperationModeStatus.ACTIVE;
        this.lastActivatedAt = activatedAt;
    }

    public void deactivate() {
        if (this.status != OperationModeStatus.ARCHIVED) {
            this.status = OperationModeStatus.INACTIVE;
        }
    }

    public void archive() {
        this.status = OperationModeStatus.ARCHIVED;
    }

    public boolean isArchived() {
        return this.status == OperationModeStatus.ARCHIVED;
    }
}
