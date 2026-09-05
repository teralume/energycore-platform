package com.teralume.energycore.notifications.interfaces.rest.transform;

import com.teralume.energycore.notifications.domain.model.commands.CreateAlertCommand;
import com.teralume.energycore.notifications.interfaces.rest.resources.CreateAlertResource;

public class CreateAlertCommandFromResourceAssembler {
    public static CreateAlertCommand toCommandFromResource(CreateAlertResource resource, Long userId) {
        return new CreateAlertCommand(
                userId,
                resource.title(),
                resource.message(),
                resource.level(),
                resource.sourceType(),
                resource.sourceId(),
                resource.sourceLabel(),
                resource.eventType(),
                resource.threadKey(),
                resource.evidence(),
                resource.explanation(),
                resource.recommendedAction(),
                resource.severityScore(),
                resource.expiresAt()
        );
    }
}
