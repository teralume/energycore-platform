package com.teralume.energycore.notifications.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.notifications.domain.model.aggregates.AlertRuleProfile;
import com.teralume.energycore.notifications.domain.repositories.AlertRuleProfileRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAlertRuleProfileRepository extends JpaRepository<AlertRuleProfile, Long>, AlertRuleProfileRepository {
}
