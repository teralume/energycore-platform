package com.teralume.energycore.workplace.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.workplace.domain.model.aggregates.Location;
import com.teralume.energycore.workplace.domain.repositories.LocationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLocationRepository extends JpaRepository<Location, Long>, LocationRepository {
}
