package com.teralume.energycore.workplace.domain.repositories;

import com.teralume.energycore.workplace.domain.model.aggregates.Location;

import java.util.List;
import java.util.Optional;

public interface LocationRepository {
    List<Location> findByUserId(Long userId);

    Optional<Location> findById(Long id);

    Location save(Location location);

    void delete(Location location);
}
