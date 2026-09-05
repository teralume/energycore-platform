package com.teralume.energycore.iam.domain.services;

import org.springframework.stereotype.Service;

@Service
public class PasswordPolicyService {

    public boolean isValid(String password) {
        return password != null && password.length() >= 8;
    }
}