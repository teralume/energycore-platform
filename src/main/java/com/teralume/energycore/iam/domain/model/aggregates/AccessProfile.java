package com.teralume.energycore.iam.domain.model.aggregates;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "access_profiles")
public class AccessProfile extends AuditableEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 255)
    private String description;
}