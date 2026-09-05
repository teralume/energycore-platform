package com.teralume.energycore.billing.interfaces.rest.resources;

import com.teralume.energycore.billing.domain.model.aggregates.Subscription;

import java.time.LocalDate;

public record SubscriptionResource(
        Long id,
        Long userId,
        String planCode,
        String status,
        boolean active,
        LocalDate startDate,
        LocalDate nextBillingDate,
        LocalDate endDate
) {
    public static SubscriptionResource from(Subscription subscription) {
        return new SubscriptionResource(
                subscription.getId(),
                subscription.getUserId(),
                subscription.getPlan().getCode(),
                subscription.getStatus().name(),
                subscription.isActive(),
                subscription.getStartDate(),
                nextBillingDateFor(subscription),
                subscription.getEndDate()
        );
    }

    private static LocalDate nextBillingDateFor(Subscription subscription) {
        if (!subscription.isActive() || subscription.getStartDate() == null) {
            return null;
        }

        return subscription.getStartDate().plusMonths(1);
    }
}
