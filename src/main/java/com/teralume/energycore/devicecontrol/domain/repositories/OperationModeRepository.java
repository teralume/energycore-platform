package com.teralume.energycore.devicecontrol.domain.repositories;

import com.teralume.energycore.devicecontrol.domain.model.OperationModeStatus;
import com.teralume.energycore.devicecontrol.domain.model.aggregates.OperationMode;

import java.util.List;
import java.util.Optional;

public interface OperationModeRepository {
    List<OperationMode> findByUserIdAndStatusNot(Long userId, OperationModeStatus status);

    List<OperationMode> findByUserIdAndLocationIdAndStatus(
            Long userId,
            Long locationId,
            OperationModeStatus status
    );

    List<OperationMode> findByUserIdAndLocationIdForUpdate(
            Long userId,
            Long locationId
    );

    List<OperationMode> findActiveByUserIdAndLocationIdForUpdate(
            Long userId,
            Long locationId,
            OperationModeStatus status
    );

    Optional<OperationMode> findByIdAndUserId(Long id, Long userId);

    Optional<OperationMode> findById(Long id);

    OperationMode save(OperationMode operationMode);
}
