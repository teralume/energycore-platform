package com.teralume.energycore.iam.domain.model.aggregates;

import com.teralume.energycore.iam.domain.model.*;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends AuditableEntity {

    @Column(name = "full_name", nullable = false, length = 120)
    private String fullName;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AccountStatus status = AccountStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "access_profile_id")
    private AccessProfile accessProfile;

    public void deactivate() {
        this.status = AccountStatus.INACTIVE;
    }

    public boolean isActive() {
        return this.status == AccountStatus.ACTIVE;
    }
}