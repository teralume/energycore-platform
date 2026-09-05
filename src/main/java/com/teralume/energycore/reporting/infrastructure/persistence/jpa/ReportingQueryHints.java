package com.teralume.energycore.reporting.infrastructure.persistence.jpa;

public final class ReportingQueryHints {

    public static final String FIND_REPORTS_BY_USER = "Find consumption reports by user id";
    public static final String FIND_GOALS_BY_USER = "Find energy goals by user id";
    public static final String FIND_ACTIVE_GOALS = "Find active energy goals";

    private ReportingQueryHints() {
    }
}