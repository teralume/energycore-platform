package com.teralume.energycore.workplace.interfaces.rest.resources;

public record UpdateLocationResource(
        String name,
        String address,
        String type
) {
}