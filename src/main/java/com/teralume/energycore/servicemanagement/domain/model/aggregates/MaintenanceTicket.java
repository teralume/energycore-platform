package com.teralume.energycore.servicemanagement.domain.model.aggregates;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "maintenance_tickets")
public class MaintenanceTicket extends AuditableEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long deviceId;

    @Column(nullable = false, length = 120)
    private String deviceName;

    @Column(nullable = false, length = 60)
    private String type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private LocalDate scheduledDate;

    @Column(nullable = false, length = 40)
    private String status;
}