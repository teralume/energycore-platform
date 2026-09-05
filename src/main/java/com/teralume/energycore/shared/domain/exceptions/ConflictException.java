package com.teralume.energycore.shared.domain.exceptions;

public class ConflictException extends DomainException {
    public ConflictException(String message) {
        super(message);
    }
}