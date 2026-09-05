package com.teralume.energycore.reporting.domain.model.aggregates;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "energy_goals")
public class EnergyGoal extends AuditableEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 160)
    private String title;

    // Legacy column name kept for existing databases; goal values are kWh, not watts.
    @Column(name = "target_watts", nullable = false, precision = 12, scale = 6)
    private BigDecimal targetKilowattHours;

    // Legacy column name kept for existing databases; goal values are kWh, not watts.
    @Column(name = "current_watts", nullable = false, precision = 12, scale = 6)
    private BigDecimal currentKilowattHours;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false, length = 40)
    private String status;

    @Column(length = 20)
    private String scopeType = "GENERAL";

    @Column
    private Long scopeId;

    @Column(length = 160)
    private String scopeName;

    @Column
    private LocalTime activeFrom;

    @Column
    private LocalTime activeTo;
}
