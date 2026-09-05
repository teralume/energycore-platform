package com.teralume.energycore.workplace.domain.model.commands;

public record CreateRoomCommand(
        Long locationId,
        String name,
        String floor
) {
}