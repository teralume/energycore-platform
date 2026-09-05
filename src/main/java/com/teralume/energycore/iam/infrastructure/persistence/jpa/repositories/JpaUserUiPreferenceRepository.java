package com.teralume.energycore.iam.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.iam.domain.model.aggregates.UserUiPreference;
import com.teralume.energycore.iam.domain.repositories.UserUiPreferenceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserUiPreferenceRepository extends JpaRepository<UserUiPreference, Long>, UserUiPreferenceRepository {
}
