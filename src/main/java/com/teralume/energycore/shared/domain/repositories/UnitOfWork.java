package com.teralume.energycore.shared.domain.repositories;

import java.util.function.Supplier;

public interface UnitOfWork {
    void execute(Runnable operation);

    <T> T execute(Supplier<T> operation);
}