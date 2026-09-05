package com.teralume.energycore.iam.application.queryservices;

import com.teralume.energycore.iam.domain.model.aggregates.User;

public interface AuthenticatedUserQueryService {
    User getAuthenticatedUser(Long userId);
}
