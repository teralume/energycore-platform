package com.teralume.energycore.iam.domain.repositories;

import com.teralume.energycore.iam.domain.model.aggregates.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findById(Long id);

    /** Serializes changes to user-owned singleton records within a transaction. */
    Optional<User> findByIdForUpdate(Long id);

    User save(User user);
}
