package com.teralume.energycore.shared.domain.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Embeddable
public class EnergyUsage implements Serializable {

    private BigDecimal watts;

    public EnergyUsage(BigDecimal watts) {
        if (watts == null || watts.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Energy usage cannot be negative.");
        }

        this.watts = watts.setScale(2, RoundingMode.HALF_UP);
    }

    public static EnergyUsage of(BigDecimal watts) {
        return new EnergyUsage(watts);
    }

    public static EnergyUsage zero() {
        return new EnergyUsage(BigDecimal.ZERO);
    }

    public EnergyUsage add(EnergyUsage other) {
        if (other == null) return this;
        return new EnergyUsage(this.watts.add(other.watts));
    }

    public boolean isHighUsage(BigDecimal threshold) {
        if (threshold == null) return false;
        return this.watts.compareTo(threshold) >= 0;
    }

    public String classify() {
        if (watts.compareTo(new BigDecimal("1500")) >= 0) return "HIGH";
        if (watts.compareTo(new BigDecimal("700")) >= 0) return "MEDIUM";
        return "LOW";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnergyUsage that)) return false;
        return Objects.equals(watts, that.watts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(watts);
    }
}