package com.teralume.energycore.reporting.domain.repositories;

import com.teralume.energycore.reporting.domain.model.aggregates.ReportingEvent;

import java.util.List;
import java.util.Optional;

public interface ReportingEventRepository {
    List<ReportingEvent> findTop50ByUserIdOrderByOccurredOnDescIdDesc(Long userId);

    Optional<ReportingEvent> findByEventKey(String eventKey);

    ReportingEvent save(ReportingEvent reportingEvent);
}
