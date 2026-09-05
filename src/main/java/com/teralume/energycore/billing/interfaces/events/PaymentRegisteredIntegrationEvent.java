package com.teralume.energycore.billing.interfaces.events;

import com.teralume.energycore.billing.domain.model.events.PaymentRegisteredEvent;
import com.teralume.energycore.shared.application.events.IntegrationEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentRegisteredIntegrationEvent(
        Long userId,
        Long paymentId,
        BigDecimal amount,
        LocalDateTime occurredOn
) implements IntegrationEvent {
    public static PaymentRegisteredIntegrationEvent from(PaymentRegisteredEvent event) {
        return new PaymentRegisteredIntegrationEvent(
                event.userId(),
                event.paymentId(),
                event.amount(),
                event.occurredOn()
        );
    }
}
