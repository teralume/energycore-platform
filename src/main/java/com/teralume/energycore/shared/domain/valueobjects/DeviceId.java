package com.teralume.energycore.shared.domain.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Embeddable
public class DeviceId implements Serializable {

    private Long value;

    public DeviceId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Device id must be greater than zero.");
        }
        this.value = value;
    }

    public static DeviceId of(Long value) {
        return new DeviceId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceId deviceId)) return false;
        return Objects.equals(value, deviceId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}