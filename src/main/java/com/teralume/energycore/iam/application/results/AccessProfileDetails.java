package com.teralume.energycore.iam.application.results;

import com.teralume.energycore.iam.domain.model.AccessPermission;
import com.teralume.energycore.iam.domain.model.aggregates.AccessProfile;

import java.util.Set;

public record AccessProfileDetails(
        AccessProfile profile,
        Set<AccessPermission> permissions
) {
}
