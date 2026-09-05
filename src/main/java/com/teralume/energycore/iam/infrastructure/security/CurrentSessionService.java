package com.teralume.energycore.iam.infrastructure.security;

import org.springframework.stereotype.Service;
import com.teralume.energycore.shared.infrastructure.security.CurrentUserProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurrentSessionService {

    private final CurrentUserProvider currentUserProvider;

    public Long getCurrentUserIdOrDefault() {
        return currentUserProvider.getCurrentUserId();
    }
}
