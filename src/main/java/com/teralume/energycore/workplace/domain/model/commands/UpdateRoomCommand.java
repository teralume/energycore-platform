package com.teralume.energycore.workplace.domain.model.commands;

public record UpdateRoomCommand(
        Long roomId,
        Long locationId,
        String name,
        String floor
) {
}