package com.teralume.energycore.notifications.interfaces.rest.controllers;

import com.teralume.energycore.notifications.application.commandservices.NotificationCommandService;
import com.teralume.energycore.notifications.application.queryservices.NotificationQueryService;
import com.teralume.energycore.notifications.domain.model.commands.ActivateAlertRuleProfileCommand;
import com.teralume.energycore.notifications.domain.model.commands.DismissAlertCommand;
import com.teralume.energycore.notifications.domain.model.commands.MarkAlertAsReadCommand;
import com.teralume.energycore.notifications.domain.model.commands.ResolveAlertCommand;
import com.teralume.energycore.notifications.domain.model.commands.ToggleAlertRuleCommand;
import com.teralume.energycore.notifications.domain.model.queries.EvaluateAlertRulesQuery;
import com.teralume.energycore.notifications.domain.model.queries.GetAlertRulesByUserQuery;
import com.teralume.energycore.notifications.domain.model.queries.GetAlertRuleProfilesByUserQuery;
import com.teralume.energycore.notifications.domain.model.queries.GetAlertsByUserQuery;
import com.teralume.energycore.notifications.domain.model.queries.GetNotificationPreferenceQuery;
import com.teralume.energycore.notifications.interfaces.rest.resources.*;
import com.teralume.energycore.notifications.interfaces.rest.transform.*;
import com.teralume.energycore.iam.application.security.AccessAuthorizationService;
import com.teralume.energycore.iam.domain.model.AccessPermission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationCommandService commandService;
    private final NotificationQueryService queryService;
    private final AccessAuthorizationService accessAuthorizationService;

    @GetMapping("/alerts")
    public List<AlertResource> getAlerts() {
        return queryService.handle(new GetAlertsByUserQuery(requireAlertManagement())).stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @PostMapping("/alerts")
    public AlertResource createAlert(@Valid @RequestBody CreateAlertResource request) {
        var command = CreateAlertCommandFromResourceAssembler.toCommandFromResource(request, requireAlertManagement());
        var alert = commandService.handle(command);
        return AlertResourceFromEntityAssembler.toResourceFromEntity(alert);
    }

    @PatchMapping("/alerts/{alertId}/read")
    public AlertResource markAsRead(@PathVariable Long alertId) {
        var alert = commandService.handle(new MarkAlertAsReadCommand(requireAlertManagement(), alertId));
        return AlertResourceFromEntityAssembler.toResourceFromEntity(alert);
    }

    @PatchMapping("/alerts/{alertId}/dismiss")
    public AlertResource dismissAlert(
            @PathVariable Long alertId,
            @RequestParam(defaultValue = "10") Integer minutes
    ) {
        var alert = commandService.handle(new DismissAlertCommand(requireAlertManagement(), alertId, minutes));
        return AlertResourceFromEntityAssembler.toResourceFromEntity(alert);
    }

    @PatchMapping("/alerts/{alertId}/resolve")
    public AlertResource resolveAlert(@PathVariable Long alertId) {
        var alert = commandService.handle(new ResolveAlertCommand(requireAlertManagement(), alertId));
        return AlertResourceFromEntityAssembler.toResourceFromEntity(alert);
    }

    @DeleteMapping("/alerts/{alertId}")
    public void deleteAlert(@PathVariable Long alertId) {
        commandService.deleteAlert(requireAlertManagement(), alertId);
    }

    @GetMapping("/alerts/rules")
    public List<AlertRuleResource> getRules() {
        return queryService.handle(new GetAlertRulesByUserQuery(requireAlertManagement())).stream()
                .map(AlertRuleResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @PostMapping("/alerts/rules")
    public AlertRuleResource createRule(@Valid @RequestBody CreateAlertRuleResource request) {
        var command = CreateAlertRuleCommandFromResourceAssembler.toCommandFromResource(request, requireAlertManagement());
        var rule = commandService.handle(command);
        return AlertRuleResourceFromEntityAssembler.toResourceFromEntity(rule);
    }

    @PatchMapping("/alerts/rules/{ruleId}/toggle")
    public AlertRuleResource toggleRule(@PathVariable Long ruleId) {
        var rule = commandService.handle(new ToggleAlertRuleCommand(requireAlertManagement(), ruleId));
        return AlertRuleResourceFromEntityAssembler.toResourceFromEntity(rule);
    }

    @DeleteMapping("/alerts/rules/{ruleId}")
    public void deleteRule(@PathVariable Long ruleId) {
        commandService.deleteRule(requireAlertManagement(), ruleId);
    }

    @GetMapping("/alerts/rule-profiles")
    public List<AlertRuleProfileResource> getRuleProfiles() {
        return queryService.handle(new GetAlertRuleProfilesByUserQuery(requireAlertManagement())).stream()
                .map(AlertRuleProfileResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @PostMapping("/alerts/rule-profiles")
    public AlertRuleProfileResource createRuleProfile(@Valid @RequestBody CreateAlertRuleProfileResource request) {
        var command = CreateAlertRuleProfileCommandFromResourceAssembler.toCommandFromResource(request, requireAlertManagement());
        var profile = commandService.handle(command);
        return AlertRuleProfileResourceFromEntityAssembler.toResourceFromEntity(profile);
    }

    @PatchMapping("/alerts/rule-profiles/{profileId}/activate")
    public AlertRuleProfileResource activateRuleProfile(@PathVariable Long profileId) {
        var profile = commandService.handle(new ActivateAlertRuleProfileCommand(requireAlertManagement(), profileId));
        return AlertRuleProfileResourceFromEntityAssembler.toResourceFromEntity(profile);
    }

    @PostMapping("/alerts/rules/evaluate")
    public RuleEvaluationResultResource evaluateRules(@Valid @RequestBody EvaluateAlertRulesResource request) {
        var result = queryService.handle(new EvaluateAlertRulesQuery(
                requireAlertManagement(),
                request.scopeType(),
                request.scopeId(),
                request.observedValue()
        ));
        return RuleEvaluationResultResource.from(result);
    }

    @GetMapping("/notifications/preferences")
    public NotificationPreferenceResource getPreference() {
        var preference = queryService.handle(new GetNotificationPreferenceQuery(requireAlertManagement()));
        return NotificationPreferenceResourceFromEntityAssembler.toResourceFromEntity(preference);
    }

    @PutMapping("/notifications/preferences")
    public NotificationPreferenceResource updatePreference(@Valid @RequestBody UpdateNotificationPreferenceResource request) {
        var command = UpdateNotificationPreferenceCommandFromResourceAssembler.toCommandFromResource(request, requireAlertManagement());
        var preference = commandService.handle(command);
        return NotificationPreferenceResourceFromEntityAssembler.toResourceFromEntity(preference);
    }

    private Long requireAlertManagement() {
        return accessAuthorizationService.requirePermission(AccessPermission.MANAGE_ALERTS);
    }
}
