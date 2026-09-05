package com.teralume.energycore.shared.application.result;

import java.util.Objects;
import java.util.function.Function;

public final class Result<TSuccess, TFailure> {

    private final TSuccess success;
    private final TFailure failure;
    private final boolean successful;

    private Result(TSuccess success, TFailure failure, boolean successful) {
        this.success = success;
        this.failure = failure;
        this.successful = successful;
    }

    public static <TSuccess, TFailure> Result<TSuccess, TFailure> success(TSuccess value) {
        return new Result<>(Objects.requireNonNull(value, "Success value is required."), null, true);
    }

    public static <TSuccess, TFailure> Result<TSuccess, TFailure> failure(TFailure value) {
        return new Result<>(null, Objects.requireNonNull(value, "Failure value is required."), false);
    }

    public boolean isSuccess() {
        return successful;
    }

    public boolean isFailure() {
        return !successful;
    }

    public TSuccess success() {
        if (isFailure()) {
            throw new IllegalStateException("Result does not contain a success value.");
        }
        return success;
    }

    public TFailure failure() {
        if (isSuccess()) {
            throw new IllegalStateException("Result does not contain a failure value.");
        }
        return failure;
    }

    public <TResult> TResult fold(
            Function<TSuccess, TResult> onSuccess,
            Function<TFailure, TResult> onFailure
    ) {
        return isSuccess() ? onSuccess.apply(success) : onFailure.apply(failure);
    }
}