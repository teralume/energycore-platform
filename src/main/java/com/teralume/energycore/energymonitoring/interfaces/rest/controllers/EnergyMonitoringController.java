package com.teralume.energycore.energymonitoring.interfaces.rest.controllers;

import com.teralume.energycore.energymonitoring.application.commandservices.EnergyMonitoringCommandService;
import com.teralume.energycore.energymonitoring.application.queryservices.EnergyMonitoringQueryService;
import com.teralume.energycore.energymonitoring.domain.model.commands.UpdateEnergySamplingSettingsCommand;
import com.teralume.energycore.energymonitoring.domain.model.queries.GetEnergyDashboardSummaryQuery;
import com.teralume.energycore.energymonitoring.domain.model.queries.GetEnergyReadingsByUserQuery;
import com.teralume.energycore.energymonitoring.domain.model.queries.GetEnergySamplingSettingsQuery;
import com.teralume.energycore.energymonitoring.interfaces.rest.resources.CreateEnergyReadingResource;
import com.teralume.energycore.energymonitoring.interfaces.rest.resources.EnergyDashboardSummaryResource;
import com.teralume.energycore.energymonitoring.interfaces.rest.resources.EnergyReadingResource;
import com.teralume.energycore.energymonitoring.interfaces.rest.resources.UpdateEnergySamplingSettingsResource;
import com.teralume.energycore.energymonitoring.interfaces.rest.resources.EnergySamplingSettingsResource;
import com.teralume.energycore.energymonitoring.interfaces.rest.transform.CreateEnergyReadingCommandFromResourceAssembler;
import com.teralume.energycore.energymonitoring.interfaces.rest.transform.EnergyReadingResourceFromEntityAssembler;
import com.teralume.energycore.iam.application.security.AccessAuthorizationService;
import com.teralume.energycore.iam.domain.model.AccessPermission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/energy-readings")
@RequiredArgsConstructor
public class EnergyMonitoringController {

    private final EnergyMonitoringCommandService commandService;
    private final EnergyMonitoringQueryService queryService;
    private final AccessAuthorizationService accessAuthorizationService;

    @GetMapping
    public List<EnergyReadingResource> getReadings(
            @RequestParam(name = "recordedAt_gte", required = false) String recordedAtGte,
            @RequestParam(name = "recordedAt_lte", required = false) String recordedAtLte
    ) {
        Long userId = requireEnergyAccess();
        if (recordedAtGte != null && recordedAtLte != null) {
            return queryService.handle(new GetEnergyReadingsByUserQuery(
                    userId,
                    parseStart(recordedAtGte),
                    parseEnd(recordedAtLte)
            )).stream()
                    .map(EnergyReadingResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        }

        return queryService.handle(new GetEnergyReadingsByUserQuery(userId, null, null)).stream()
                .map(EnergyReadingResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @GetMapping("/dashboard-summary")
    public EnergyDashboardSummaryResource getDashboardSummary() {
        return queryService.handle(new GetEnergyDashboardSummaryQuery(requireEnergyAccess()));
    }

    @GetMapping("/sampling-settings")
    public EnergySamplingSettingsResource getSamplingSettings() {
        requireEnergyAccess();
        return new EnergySamplingSettingsResource(queryService.handle(new GetEnergySamplingSettingsQuery()));
    }

    @PatchMapping("/sampling-settings")
    public EnergySamplingSettingsResource updateSamplingSettings(
            @Valid @RequestBody UpdateEnergySamplingSettingsResource request
    ) {
        requireEnergyAccess();
        return new EnergySamplingSettingsResource(
                commandService.handle(new UpdateEnergySamplingSettingsCommand(request.sampleSeconds()))
        );
    }

    @PostMapping
    public EnergyReadingResource createReading(@Valid @RequestBody CreateEnergyReadingResource request) {
        var command = CreateEnergyReadingCommandFromResourceAssembler.toCommandFromResource(request, requireEnergyAccess());
        var reading = commandService.handle(command);
        return EnergyReadingResourceFromEntityAssembler.toResourceFromEntity(reading);
    }

    private Long requireEnergyAccess() {
        return accessAuthorizationService.requirePermission(AccessPermission.VIEW_ENERGY);
    }

    private LocalDateTime parseStart(String value) {
        return LocalDate.parse(value).atStartOfDay();
    }

    private LocalDateTime parseEnd(String value) {
        return LocalDate.parse(value).atTime(LocalTime.MAX);
    }
}
