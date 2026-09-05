package com.teralume.energycore.iam.application.results;

import com.teralume.energycore.iam.domain.model.aggregates.User;

public record AuthenticationResult(
        User user,
        String token
) {
}