package com.teralume.energycore.workplace.domain.model.aggregates;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room extends AuditableEntity {

    @Column(nullable = false)
    private Long locationId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 40)
    private String floor;
}