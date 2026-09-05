package com.teralume.energycore.iam.domain.model.commands;

public record UpdateUiPreferenceCommand(
        String language,
        String theme
) {
}
