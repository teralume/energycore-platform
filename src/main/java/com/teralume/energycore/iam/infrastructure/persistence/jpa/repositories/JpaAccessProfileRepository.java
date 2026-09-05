package com.teralume.energycore.iam.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.iam.domain.model.aggregates.AccessProfile;
import com.teralume.energycore.iam.domain.repositories.AccessProfileRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccessProfileRepository extends JpaRepository<AccessProfile, Long>, AccessProfileRepository {
}
