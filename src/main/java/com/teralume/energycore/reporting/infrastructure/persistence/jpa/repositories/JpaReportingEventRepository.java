package com.teralume.energycore.reporting.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.reporting.domain.model.aggregates.ReportingEvent;
import com.teralume.energycore.reporting.domain.repositories.ReportingEventRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReportingEventRepository extends JpaRepository<ReportingEvent, Long>, ReportingEventRepository {
}
