package com.teralume.energycore.shared.infrastructure.persistence.jpa;

import com.teralume.energycore.shared.domain.repositories.UnitOfWork;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Component
public class JpaUnitOfWork implements UnitOfWork {

    @Override
    @Transactional
    public void execute(Runnable operation) {
        operation.run();
    }

    @Override
    @Transactional
    public <T> T execute(Supplier<T> operation) {
        return operation.get();
    }
}