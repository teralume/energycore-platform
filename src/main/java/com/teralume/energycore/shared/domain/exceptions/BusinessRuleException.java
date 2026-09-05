package com.teralume.energycore.shared.domain.exceptions;

public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }
}