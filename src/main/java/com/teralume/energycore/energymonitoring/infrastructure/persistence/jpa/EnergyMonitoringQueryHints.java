package com.teralume.energycore.energymonitoring.infrastructure.persistence.jpa;

public final class EnergyMonitoringQueryHints {

    public static final String FIND_BY_USER = "Find energy readings by user id";
    public static final String FIND_BY_DEVICE = "Find energy readings by device id";
    public static final String FIND_BY_DATE_RANGE = "Find energy readings by recorded date range";

    private EnergyMonitoringQueryHints() {
    }
}