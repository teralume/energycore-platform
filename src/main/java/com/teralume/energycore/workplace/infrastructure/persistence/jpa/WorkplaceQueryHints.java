package com.teralume.energycore.workplace.infrastructure.persistence.jpa;

public final class WorkplaceQueryHints {

    public static final String FIND_LOCATIONS_BY_USER = "Find locations by user id";
    public static final String FIND_ROOMS_BY_LOCATION = "Find rooms by location id";
    public static final String FIND_ASSIGNMENTS_BY_LOCATION = "Find device assignments by location id";

    private WorkplaceQueryHints() {
    }
}