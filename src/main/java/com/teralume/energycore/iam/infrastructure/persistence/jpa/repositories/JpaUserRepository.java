package com.teralume.energycore.iam.infrastructure.persistence.jpa.repositories;

import com.teralume.energycore.iam.domain.model.aggregates.User;
import com.teralume.energycore.iam.domain.repositories.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
}
