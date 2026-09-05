package com.teralume.energycore.notifications.interfaces.rest.resources;

import com.teralume.energycore.notifications.domain.model.aggregates.AlertRuleProfile;

public record AlertRuleProfileResource(
        Long id,
        Long userId,
        String name,
        String description,
        String scopeType,
        String scopeId,
        String mode,
        String sensitivity,
        Boolean active
) {
    public static AlertRuleProfileResource from(AlertRuleProfile profile) {
        return new AlertRuleProfileResource(
                profile.getId(),
                profile.getUserId(),
                profile.getName(),
                profile.getDescription(),
                profile.getScopeType() == null ? null : profile.getScopeType().name(),
                profile.getScopeId(),
                profile.getMode() == null ? null : profile.getMode().name(),
                profile.getSensitivity() == null ? null : profile.getSensitivity().name(),
                profile.getActive()
        );
    }
}
