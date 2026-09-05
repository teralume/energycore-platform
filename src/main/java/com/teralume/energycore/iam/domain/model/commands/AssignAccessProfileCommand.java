package com.teralume.energycore.iam.domain.model.commands;

public record AssignAccessProfileCommand(
        Long actorUserId,
        Long targetUserId,
        Long accessProfileId
) {
}
