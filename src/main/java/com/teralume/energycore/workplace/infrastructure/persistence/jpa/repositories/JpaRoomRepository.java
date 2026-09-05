package com.teralume.energycore.workplace.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.workplace.domain.model.aggregates.Room;
import com.teralume.energycore.workplace.domain.repositories.RoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoomRepository extends JpaRepository<Room, Long>, RoomRepository {
}
