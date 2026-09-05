package com.teralume.energycore.devicecontrol.infrastructure.persistence.jpa;

public final class DeviceControlQueryHints {

    public static final String FIND_ACTIVE_DEVICES = "Find devices with status ON";
    public static final String FIND_ENABLED_ROUTINES = "Find routines with enabled true";
    public static final String FIND_GROUP_RELATIONS = "Find devices related to a device group";

    private DeviceControlQueryHints() {
    }
}