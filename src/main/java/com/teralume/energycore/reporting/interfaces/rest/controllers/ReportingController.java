package com.teralume.energycore.reporting.interfaces.rest.controllers;

import com.teralume.energycore.reporting.application.commandservices.ReportingCommandService;
import com.teralume.energycore.reporting.application.queryservices.ReportingQueryService;
import com.teralume.energycore.reporting.domain.model.queries.GetConsumptionReportsByUserQuery;
import com.teralume.energycore.reporting.domain.model.queries.GetEnergyGoalsByUserQuery;
import com.teralume.energycore.reporting.domain.model.queries.GetReportingEventsByUserQuery;
import com.teralume.energycore.reporting.interfaces.rest.resources.*;
import com.teralume.energycore.reporting.interfaces.rest.transform.ConsumptionReportResourceFromEntityAssembler;
import com.teralume.energycore.reporting.interfaces.rest.transform.CreateConsumptionReportCommandFromResourceAssembler;
import com.teralume.energycore.reporting.interfaces.rest.transform.CreateEnergyGoalCommandFromResourceAssembler;
import com.teralume.energycore.reporting.interfaces.rest.transform.EnergyGoalResourceFromEntityAssembler;
import com.teralume.energycore.reporting.interfaces.rest.transform.ReportingEventResourceFromEntityAssembler;
import com.teralume.energycore.reporting.interfaces.rest.transform.UpdateEnergyGoalCommandFromResourceAssembler;
import com.teralume.energycore.iam.application.security.AccessAuthorizationService;
import com.teralume.energycore.iam.domain.model.AccessPermission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingCommandService commandService;
    private final ReportingQueryService queryService;
    private final AccessAuthorizationService accessAuthorizationService;

    @GetMapping
    public List<ConsumptionReportResource> getReports() {
        return queryService.handle(new GetConsumptionReportsByUserQuery(requireReportAccess()))
                .stream()
                .map(ConsumptionReportResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @GetMapping("/activity")
    public List<ReportingEventResource> getActivity() {
        return queryService.handle(new GetReportingEventsByUserQuery(requireReportAccess()))
                .stream()
                .map(ReportingEventResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @PostMapping
    public ConsumptionReportResource createReport(
            @Valid @RequestBody CreateConsumptionReportResource request
    ) {
        return ConsumptionReportResourceFromEntityAssembler.toResourceFromEntity(
                commandService.handle(CreateConsumptionReportCommandFromResourceAssembler.toCommandFromResource(request, requireReportAccess()))
        );
    }

    @DeleteMapping("/{reportId}")
    public void deleteReport(@PathVariable Long reportId) {
        commandService.deleteReport(requireReportAccess(), reportId);
    }

    @GetMapping("/energy-goals")
    public List<EnergyGoalResource> getGoals() {
        return queryService.handle(new GetEnergyGoalsByUserQuery(requireReportAccess()))
                .stream()
                .map(EnergyGoalResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @PostMapping("/energy-goals")
    public EnergyGoalResource createGoal(@Valid @RequestBody CreateEnergyGoalResource request) {
        return EnergyGoalResourceFromEntityAssembler.toResourceFromEntity(
                commandService.handle(CreateEnergyGoalCommandFromResourceAssembler.toCommandFromResource(request, requireReportAccess()))
        );
    }

    @PatchMapping("/energy-goals/{goalId}")
    public EnergyGoalResource updateGoal(
            @PathVariable Long goalId,
            @RequestBody UpdateEnergyGoalResource request
    ) {
        return EnergyGoalResourceFromEntityAssembler.toResourceFromEntity(
                commandService.handle(UpdateEnergyGoalCommandFromResourceAssembler.toCommandFromResource(
                        request,
                        requireReportAccess(),
                        goalId
                ))
        );
    }

    @DeleteMapping("/energy-goals/{goalId}")
    public void deleteGoal(@PathVariable Long goalId) {
        commandService.deleteGoal(requireReportAccess(), goalId);
    }

    private Long requireReportAccess() {
        return accessAuthorizationService.requirePermission(AccessPermission.VIEW_REPORTS);
    }
}
