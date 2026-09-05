package com.teralume.energycore.energymonitoring.interfaces.events;

import com.teralume.energycore.energymonitoring.domain.model.events.EnergyThresholdExceededEvent;
import com.teralume.energycore.shared.application.events.IntegrationEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EnergyThresholdExceededIntegrationEvent(
        Long userId,
        Long deviceId,
        BigDecimal watts,
        LocalDateTime occurredOn
) implements IntegrationEvent {
    public static EnergyThresholdExceededIntegrationEvent from(EnergyThresholdExceededEvent event) {
        return new EnergyThresholdExceededIntegrationEvent(
                event.userId(),
                event.deviceId(),
                event.watts(),
                event.occurredOn()
        );
    }
}
