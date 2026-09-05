package com.teralume.energycore.workplace.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.workplace.domain.model.aggregates.DeviceAssignment;
import com.teralume.energycore.workplace.domain.repositories.DeviceAssignmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDeviceAssignmentRepository extends JpaRepository<DeviceAssignment, Long>, DeviceAssignmentRepository {
}
