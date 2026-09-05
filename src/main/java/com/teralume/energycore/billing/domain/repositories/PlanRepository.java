package com.teralume.energycore.billing.domain.repositories;

import com.teralume.energycore.billing.domain.model.aggregates.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanRepository {
    Optional<Plan> findByCode(String code);

    List<Plan> findAll();
}
