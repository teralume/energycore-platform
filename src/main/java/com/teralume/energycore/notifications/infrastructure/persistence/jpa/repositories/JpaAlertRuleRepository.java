package com.teralume.energycore.notifications.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.notifications.domain.model.aggregates.AlertRule;
import com.teralume.energycore.notifications.domain.repositories.AlertRuleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAlertRuleRepository extends JpaRepository<AlertRule, Long>, AlertRuleRepository {
}
