package com.teralume.energycore.reporting.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.reporting.domain.model.aggregates.EnergyGoal;
import com.teralume.energycore.reporting.domain.repositories.EnergyGoalRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEnergyGoalRepository extends JpaRepository<EnergyGoal, Long>, EnergyGoalRepository {
}
