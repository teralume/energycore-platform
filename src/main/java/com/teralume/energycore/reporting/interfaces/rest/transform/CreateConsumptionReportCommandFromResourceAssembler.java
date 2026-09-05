package com.teralume.energycore.reporting.interfaces.rest.transform;

import com.teralume.energycore.reporting.domain.model.commands.CreateConsumptionReportCommand;
import com.teralume.energycore.reporting.interfaces.rest.resources.CreateConsumptionReportResource;

public class CreateConsumptionReportCommandFromResourceAssembler {

    private CreateConsumptionReportCommandFromResourceAssembler() {
    }

    public static CreateConsumptionReportCommand toCommandFromResource(CreateConsumptionReportResource resource, Long userId) {
        return new CreateConsumptionReportCommand(
                userId,
                resource.startDate(),
                resource.endDate()
        );
    }
}
