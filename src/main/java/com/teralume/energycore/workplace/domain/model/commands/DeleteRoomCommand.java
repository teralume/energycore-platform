package com.teralume.energycore.workplace.domain.model.commands;

public record DeleteRoomCommand(
        Long roomId,
        Long locationId
) {
}