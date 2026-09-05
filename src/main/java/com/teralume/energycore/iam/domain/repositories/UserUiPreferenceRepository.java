package com.teralume.energycore.iam.domain.repositories;

import com.teralume.energycore.iam.domain.model.aggregates.UserUiPreference;

import java.util.Optional;

public interface UserUiPreferenceRepository {
    Optional<UserUiPreference> findByUserId(Long userId);

    UserUiPreference save(UserUiPreference preference);
}
