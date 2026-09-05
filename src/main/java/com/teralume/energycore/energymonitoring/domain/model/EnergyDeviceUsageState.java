package com.teralume.energycore.energymonitoring.domain.model;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "energy_device_usage_states")
public class EnergyDeviceUsageState extends AuditableEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "device_id", nullable = false, unique = true)
    private Long deviceId;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "last_recorded_at", nullable = false)
    private LocalDateTime lastRecordedAt;
}