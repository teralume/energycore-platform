package com.teralume.energycore.billing.domain.repositories;

import com.teralume.energycore.billing.domain.model.aggregates.Subscription;
import com.teralume.energycore.billing.domain.model.SubscriptionStatus;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    List<Subscription> findByUserId(Long userId);

    Optional<Subscription> findFirstByUserIdAndStatus(Long userId, SubscriptionStatus status);

    Optional<Subscription> findById(Long id);

    Subscription save(Subscription subscription);
}
