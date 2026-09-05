package com.teralume.energycore.notifications.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.notifications.domain.model.aggregates.Alert;
import com.teralume.energycore.notifications.domain.repositories.AlertRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAlertRepository extends JpaRepository<Alert, Long>, AlertRepository {
}
