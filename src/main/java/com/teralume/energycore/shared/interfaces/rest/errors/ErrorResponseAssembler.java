package com.teralume.energycore.shared.interfaces.rest.errors;

import com.teralume.energycore.shared.application.result.ApplicationError;
import com.teralume.energycore.shared.interfaces.rest.resources.ErrorResource;

import java.time.LocalDateTime;

public final class ErrorResponseAssembler {

    private ErrorResponseAssembler() {
    }

    public static ErrorResource toResourceFromError(ApplicationError error) {
        return new ErrorResource(error.code(), error.message(), LocalDateTime.now());
    }
}
