package com.teralume.energycore.iam.application.security;

import com.teralume.energycore.iam.domain.model.aggregates.User;

import java.util.Optional;

public interface TokenService {
    String generateToken(User user);

    Optional<Long> validateAndGetUserId(String token);
}
