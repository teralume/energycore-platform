package com.teralume.energycore.reporting.interfaces.rest.transform;

import com.teralume.energycore.reporting.domain.model.aggregates.ConsumptionReport;
import com.teralume.energycore.reporting.interfaces.rest.resources.ConsumptionReportResource;

public class ConsumptionReportResourceFromEntityAssembler {

    private ConsumptionReportResourceFromEntityAssembler() {
    }

    public static ConsumptionReportResource toResourceFromEntity(ConsumptionReport entity) {
        return ConsumptionReportResource.from(entity);
    }
}