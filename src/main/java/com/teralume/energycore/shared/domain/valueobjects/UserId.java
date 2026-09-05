package com.teralume.energycore.shared.domain.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Embeddable
public class UserId implements Serializable {

    private Long value;

    public UserId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("User id must be greater than zero.");
        }
        this.value = value;
    }

    public static UserId of(Long value) {
        return new UserId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId userId)) return false;
        return Objects.equals(value, userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}