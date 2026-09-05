package com.teralume.energycore.notifications.domain.repositories;

import com.teralume.energycore.notifications.domain.model.aggregates.AlertRuleProfile;

import java.util.List;
import java.util.Optional;

public interface AlertRuleProfileRepository {
    List<AlertRuleProfile> findByUserId(Long userId);

    Optional<AlertRuleProfile> findByIdAndUserId(Long id, Long userId);

    AlertRuleProfile save(AlertRuleProfile alertRuleProfile);
}
