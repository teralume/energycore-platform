package com.teralume.energycore.reporting.domain.repositories;

import com.teralume.energycore.reporting.domain.model.aggregates.ConsumptionReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConsumptionReportRepository {
    List<ConsumptionReport> findByUserId(Long userId);

    List<ConsumptionReport> findByUserIdOrderByStartDateAscEndDateAscIdAsc(Long userId);

    List<ConsumptionReport> findByUserIdAndStartDateAndEndDateOrderByIdAsc(Long userId, LocalDate startDate, LocalDate endDate);

    Optional<ConsumptionReport> findById(Long id);

    ConsumptionReport save(ConsumptionReport consumptionReport);

    void delete(ConsumptionReport consumptionReport);
}
