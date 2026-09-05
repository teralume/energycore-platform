package com.teralume.energycore.workplace.domain.model.commands;

public record DeleteLocationCommand(
        Long userId,
        Long locationId
) {
}