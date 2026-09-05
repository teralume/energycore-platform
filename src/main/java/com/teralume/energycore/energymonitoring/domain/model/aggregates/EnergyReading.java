package com.teralume.energycore.energymonitoring.domain.model.aggregates;

import com.teralume.energycore.energymonitoring.domain.model.*;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "energy_readings")
public class EnergyReading extends AuditableEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "device_id", nullable = false)
    private Long deviceId;

    @Column(name = "device_name", nullable = false, length = 120)
    private String deviceName;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @Column(name = "watts", nullable = false, precision = 10, scale = 2)
    private BigDecimal watts;

    @Column(name = "kilowatt_hours", precision = 12, scale = 6)
    private BigDecimal kilowattHours = BigDecimal.ZERO;

    @Column(name = "estimated_cost", precision = 12, scale = 6)
    private BigDecimal estimatedCost = BigDecimal.ZERO;

    @Column(name = "sample_seconds")
    private Integer sampleSeconds = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private EnergyReadingStatus status;
}