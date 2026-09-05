package com.teralume.energycore.devicecontrol.domain.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceGroupPolicyService {

    public boolean canCreateGroup(String name, List<Long> deviceIds) {
        return name != null
                && !name.isBlank()
                && deviceIds != null
                && !deviceIds.isEmpty();
    }
}