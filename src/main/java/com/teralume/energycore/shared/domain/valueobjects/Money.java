package com.teralume.energycore.shared.domain.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Money {

    public static final String DEFAULT_CURRENCY = "PEN";

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", length = 10)
    private String currency;

    public Money(BigDecimal amount, String currency) {
        if (amount == null) {
            throw new IllegalArgumentException("Money amount is required.");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money amount cannot be negative.");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Money currency is required.");
        }

        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency.trim().toUpperCase();
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount, DEFAULT_CURRENCY);
    }

    public static Money of(BigDecimal amount, String currency) {
        return new Money(amount, currency);
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO, DEFAULT_CURRENCY);
    }

    public Money add(Money other) {
        validateSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        validateSameCurrency(other);
        BigDecimal result = this.amount.subtract(other.amount);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money subtraction cannot produce a negative amount.");
        }
        return new Money(result, this.currency);
    }

    public Money multiply(BigDecimal factor) {
        if (factor == null || factor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money factor cannot be negative.");
        }
        return new Money(this.amount.multiply(factor), this.currency);
    }

    public boolean isZero() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean isPositive() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money other) {
        validateSameCurrency(other);
        return this.amount.compareTo(other.amount) > 0;
    }

    private void validateSameCurrency(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("Money value is required.");
        }
        if (!Objects.equals(this.currency, other.currency)) {
            throw new IllegalArgumentException("Money currencies must match.");
        }
    }
}