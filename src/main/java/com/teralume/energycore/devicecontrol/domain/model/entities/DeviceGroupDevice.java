package com.teralume.energycore.devicecontrol.domain.model.entities;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "device_group_devices")
public class DeviceGroupDevice extends AuditableEntity {

    @Column(nullable = false)
    private Long deviceGroupId;

    @Column(nullable = false)
    private Long deviceId;
}
