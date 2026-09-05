package com.teralume.energycore.workplace.domain.repositories;

import com.teralume.energycore.workplace.domain.model.aggregates.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    List<Room> findByLocationId(Long locationId);

    List<Room> findByLocationIdIn(List<Long> locationIds);

    Optional<Room> findById(Long id);

    Room save(Room room);

    void delete(Room room);
}
