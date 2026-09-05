package com.teralume.energycore.reporting.domain.model.valueobjects;

import com.teralume.energycore.shared.domain.valueobjects.Money;

public record ReportEstimatedCost(Money value) {
    public ReportEstimatedCost {
        if (value == null) {
            throw new IllegalArgumentException("Report estimated cost is required.");
        }
    }
}