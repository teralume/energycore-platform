package com.teralume.energycore.iam.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.iam.domain.model.entities.PasswordResetToken;
import com.teralume.energycore.iam.domain.repositories.PasswordResetTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>, PasswordResetTokenRepository {
}
