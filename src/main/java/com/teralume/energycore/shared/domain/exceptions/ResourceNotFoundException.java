package com.teralume.energycore.shared.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " with id " + id + " was not found.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}