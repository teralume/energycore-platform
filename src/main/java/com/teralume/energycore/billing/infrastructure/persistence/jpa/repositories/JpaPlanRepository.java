package com.teralume.energycore.billing.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.billing.domain.model.aggregates.Plan;
import com.teralume.energycore.billing.domain.repositories.PlanRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPlanRepository extends JpaRepository<Plan, Long>, PlanRepository {
}
