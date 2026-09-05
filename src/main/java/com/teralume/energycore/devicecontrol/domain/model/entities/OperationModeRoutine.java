package com.teralume.energycore.devicecontrol.domain.model.entities;

import com.teralume.energycore.devicecontrol.domain.model.RoutineAction;
import com.teralume.energycore.devicecontrol.domain.model.RoutineTargetType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OperationModeRoutine {

    @Column(nullable = false, length = 120)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoutineTargetType targetType;

    @Column(nullable = false)
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoutineAction action;

    @Column(nullable = false, length = 10)
    private String triggerTime;

    @Column(nullable = false)
    private Boolean enabled = true;
}
