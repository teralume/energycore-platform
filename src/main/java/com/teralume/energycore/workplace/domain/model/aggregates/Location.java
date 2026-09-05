package com.teralume.energycore.workplace.domain.model.aggregates;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location extends AuditableEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 255)
    private String address;

    @Column(length = 50)
    private String type;
}