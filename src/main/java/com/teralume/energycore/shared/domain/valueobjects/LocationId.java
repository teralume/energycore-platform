package com.teralume.energycore.shared.domain.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Embeddable
public class LocationId implements Serializable {

    private Long value;

    public LocationId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Location id must be greater than zero.");
        }
        this.value = value;
    }

    public static LocationId of(Long value) {
        return new LocationId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationId locationId)) return false;
        return Objects.equals(value, locationId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}