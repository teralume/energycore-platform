package com.teralume.energycore.notifications.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.notifications.domain.model.aggregates.NotificationPreference;
import com.teralume.energycore.notifications.domain.repositories.NotificationPreferenceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaNotificationPreferenceRepository extends JpaRepository<NotificationPreference, Long>, NotificationPreferenceRepository {
}
