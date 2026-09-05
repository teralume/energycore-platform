package com.teralume.energycore.notifications.domain.repositories;

import com.teralume.energycore.notifications.domain.model.aggregates.NotificationPreference;

import java.util.Optional;

public interface NotificationPreferenceRepository {
    Optional<NotificationPreference> findByUserId(Long userId);

    Optional<NotificationPreference> findById(Long id);

    NotificationPreference save(NotificationPreference notificationPreference);
}
