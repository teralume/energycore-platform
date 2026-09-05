package com.teralume.energycore.reporting.domain.model.commands;

import java.time.LocalDateTime;

public record RecordReportingEventCommand(
        Long userId,
        String eventName,
        String sourceContext,
        String subjectType,
        String subjectId,
        String summary,
        String detail,
        LocalDateTime occurredOn
) {
}
