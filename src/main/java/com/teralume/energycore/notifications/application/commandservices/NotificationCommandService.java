package com.teralume.energycore.notifications.application.commandservices;

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

public interface NotificationCommandService {
    Alert handle(CreateAlertCommand command);
    Alert handle(MarkAlertAsReadCommand command);
    Alert handle(DismissAlertCommand command);
    Alert handle(ResolveAlertCommand command);
    void deleteAlert(Long userId, Long alertId);
    AlertRule handle(CreateAlertRuleCommand command);
    AlertRule handle(ToggleAlertRuleCommand command);
    void deleteRule(Long userId, Long ruleId);
    AlertRuleProfile handle(CreateAlertRuleProfileCommand command);
    AlertRuleProfile handle(ActivateAlertRuleProfileCommand command);
    NotificationPreference handle(UpdateNotificationPreferenceCommand command);
}
