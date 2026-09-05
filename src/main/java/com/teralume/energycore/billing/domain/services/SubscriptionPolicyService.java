package com.teralume.energycore.billing.domain.services;

import com.teralume.energycore.billing.domain.model.aggregates.Subscription;
import com.teralume.energycore.billing.domain.model.SubscriptionStatus;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionPolicyService {

    public boolean hasActiveSubscription(Subscription subscription) {
        return subscription != null && subscription.getStatus() == SubscriptionStatus.ACTIVE;
    }
}