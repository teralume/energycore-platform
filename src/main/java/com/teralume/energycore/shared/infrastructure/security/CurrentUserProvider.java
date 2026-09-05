package com.teralume.energycore.shared.infrastructure.security;

import org.springframework.stereotype.Component;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class CurrentUserProvider {

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authenticated user is required.");
        }

        return Long.valueOf(authentication.getPrincipal().toString());
    }
}
