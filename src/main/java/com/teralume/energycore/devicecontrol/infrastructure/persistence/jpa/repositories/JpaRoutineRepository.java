package com.teralume.energycore.devicecontrol.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.devicecontrol.domain.model.aggregates.Routine;
import com.teralume.energycore.devicecontrol.domain.repositories.RoutineRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoutineRepository extends JpaRepository<Routine, Long>, RoutineRepository {
}
