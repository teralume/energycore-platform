package com.teralume.energycore.shared.domain.model;

import java.time.LocalDateTime;

public interface DomainEvent {
    LocalDateTime occurredOn();
}