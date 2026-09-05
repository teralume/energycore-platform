package com.teralume.energycore.devicecontrol.domain.model.aggregates;

import com.teralume.energycore.devicecontrol.domain.model.*;

import com.teralume.energycore.shared.domain.model.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devices")
public class Device extends AuditableEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 120)
    private String room;

    @Column(nullable = false, length = 40)
    private String type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal powerWatts;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DeviceStatus status = DeviceStatus.OFF;

    public void toggle() {
        if (!canReceiveOperationalChanges()) {
            throw new IllegalStateException("Device cannot be toggled in its current status.");
        }
        this.status = this.status == DeviceStatus.ON ? DeviceStatus.OFF : DeviceStatus.ON;
    }

    public void turnOn() {
        if (!canReceiveOperationalChanges()) {
            throw new IllegalStateException("Device cannot be turned on in its current status.");
        }
        this.status = DeviceStatus.ON;
    }

    public void turnOff() {
        if (this.status == DeviceStatus.REMOVED) {
            throw new IllegalStateException("Removed device cannot be turned off.");
        }
        this.status = DeviceStatus.OFF;
    }

    public void placeInMaintenance() {
        if (this.status == DeviceStatus.REMOVED) {
            throw new IllegalStateException("Removed device cannot enter maintenance.");
        }
        this.status = DeviceStatus.MAINTENANCE;
    }

    public void markAsRemoved() {
        this.status = DeviceStatus.REMOVED;
    }

    public boolean isOn() {
        return this.status == DeviceStatus.ON;
    }

    public boolean isRemoved() {
        return this.status == DeviceStatus.REMOVED;
    }

    public boolean isInMaintenance() {
        return this.status == DeviceStatus.MAINTENANCE;
    }

    public boolean canReceiveOperationalChanges() {
        return this.status == DeviceStatus.ON || this.status == DeviceStatus.OFF;
    }
}
