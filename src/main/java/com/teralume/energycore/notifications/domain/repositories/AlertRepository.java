package com.teralume.energycore.notifications.domain.repositories;

import com.teralume.energycore.notifications.domain.model.aggregates.Alert;

import java.util.List;
import java.util.Optional;

public interface AlertRepository {
    List<Alert> findByUserId(Long userId);

    List<Alert> findByUserIdAndActiveTrue(Long userId);

    Optional<Alert> findByIdAndUserId(Long id, Long userId);

    Optional<Alert> findFirstByUserIdAndThreadKeyAndActiveTrueOrderByLastTriggeredAtDesc(Long userId, String threadKey);

    Alert save(Alert alert);

    void delete(Alert alert);
}
