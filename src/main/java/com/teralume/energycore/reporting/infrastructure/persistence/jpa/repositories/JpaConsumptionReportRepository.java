package com.teralume.energycore.reporting.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.reporting.domain.model.aggregates.ConsumptionReport;
import com.teralume.energycore.reporting.domain.repositories.ConsumptionReportRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaConsumptionReportRepository extends JpaRepository<ConsumptionReport, Long>, ConsumptionReportRepository {
}
