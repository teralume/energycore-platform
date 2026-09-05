package com.teralume.energycore.notifications.domain.repositories;

import com.teralume.energycore.notifications.domain.model.aggregates.AlertRule;

import java.util.List;
import java.util.Optional;

public interface AlertRuleRepository {
    List<AlertRule> findByUserId(Long userId);

    List<AlertRule> findByUserIdAndEnabledTrue(Long userId);

    Optional<AlertRule> findByIdAndUserId(Long id, Long userId);

    AlertRule save(AlertRule alertRule);

    void delete(AlertRule alertRule);
}
