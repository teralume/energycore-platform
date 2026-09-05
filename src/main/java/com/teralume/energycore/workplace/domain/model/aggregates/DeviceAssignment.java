package com.teralume.energycore.workplace.domain.model.aggregates;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "device_assignments")
public class DeviceAssignment extends AuditableEntity {

    @Column(nullable = false)
    private Long deviceId;

    @Column(nullable = true)
    private Long roomId;

    @Column(nullable = false)
    private Long locationId;

    @Column(nullable = false)
    private LocalDateTime assignedAt;
}
