package com.teralume.energycore.iam.application.security;

public interface HashingService {
    String hash(String rawValue);

    boolean matches(String rawValue, String hashedValue);
}
