package com.teralume.energycore.notifications.application.internal.commandservices;

import com.teralume.energycore.notifications.application.commandservices.NotificationCommandService;
import com.teralume.energycore.notifications.application.services.NotificationApplicationService;
import com.teralume.energycore.notifications.domain.model.aggregates.Alert;
import com.teralume.energycore.notifications.domain.model.aggregates.AlertRule;
import com.teralume.energycore.notifications.domain.model.aggregates.AlertRuleProfile;
import com.teralume.energycore.notifications.domain.model.aggregates.NotificationPreference;
import com.teralume.energycore.notifications.domain.model.commands.ActivateAlertRuleProfileCommand;
import com.teralume.energycore.notifications.domain.model.commands.CreateAlertCommand;
import com.teralume.energycore.notifications.domain.model.commands.CreateAlertRuleProfileCommand;
import com.teralume.energycore.notifications.domain.model.commands.CreateAlertRuleCommand;
import com.teralume.energycore.notifications.domain.model.commands.DismissAlertCommand;
import com.teralume.energycore.notifications.domain.model.commands.MarkAlertAsReadCommand;
import com.teralume.energycore.notifications.domain.model.commands.ResolveAlertCommand;
import com.teralume.energycore.notifications.domain.model.commands.ToggleAlertRuleCommand;
import com.teralume.energycore.notifications.domain.model.commands.UpdateNotificationPreferenceCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationCommandServiceImpl implements NotificationCommandService {
    private final NotificationApplicationService applicationService;

    @Override
    public Alert handle(CreateAlertCommand command) {
        return applicationService.createAlert(command);
    }

    @Override
    public Alert handle(MarkAlertAsReadCommand command) {
        return applicationService.markAsRead(command.userId(), command.alertId());
    }

    @Override
    public Alert handle(DismissAlertCommand command) {
        return applicationService.dismissAlert(command.userId(), command.alertId(), command.minutes());
    }

    @Override
    public Alert handle(ResolveAlertCommand command) {
        return applicationService.resolveAlert(command.userId(), command.alertId());
    }

    @Override
    public void deleteAlert(Long userId, Long alertId) {
        applicationService.deleteAlert(userId, alertId);
    }

    @Override
    public AlertRule handle(CreateAlertRuleCommand command) {
        return applicationService.createRule(command);
    }

    @Override
    public AlertRule handle(ToggleAlertRuleCommand command) {
        return applicationService.toggleRule(command.userId(), command.ruleId());
    }

    @Override
    public void deleteRule(Long userId, Long ruleId) {
        applicationService.deleteRule(userId, ruleId);
    }

    @Override
    public AlertRuleProfile handle(CreateAlertRuleProfileCommand command) {
        return applicationService.createRuleProfile(command);
    }

    @Override
    public AlertRuleProfile handle(ActivateAlertRuleProfileCommand command) {
        return applicationService.activateRuleProfile(command.userId(), command.profileId());
    }

    @Override
    public NotificationPreference handle(UpdateNotificationPreferenceCommand command) {
        return applicationService.updatePreference(command);
    }
}
