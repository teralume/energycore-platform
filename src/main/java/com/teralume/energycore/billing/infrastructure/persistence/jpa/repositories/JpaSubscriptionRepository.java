package com.teralume.energycore.billing.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.billing.domain.model.aggregates.Subscription;
import com.teralume.energycore.billing.domain.repositories.SubscriptionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSubscriptionRepository extends JpaRepository<Subscription, Long>, SubscriptionRepository {
}
