package com.teralume.energycore.iam.application.security;

public interface PasswordResetTokenService {
    String generateToken();

    String hashToken(String token);
}
