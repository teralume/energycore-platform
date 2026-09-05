package com.teralume.energycore.shared.application.events;

import java.time.LocalDateTime;

public interface IntegrationEvent {
    LocalDateTime occurredOn();
}
