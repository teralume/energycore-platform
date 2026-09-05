package com.teralume.energycore.notifications.application.queryservices;

import com.teralume.energycore.notifications.domain.model.aggregates.Alert;
import com.teralume.energycore.notifications.domain.model.aggregates.AlertRule;
import com.teralume.energycore.notifications.domain.model.aggregates.AlertRuleProfile;
import com.teralume.energycore.notifications.domain.model.aggregates.NotificationPreference;
import com.teralume.energycore.notifications.domain.model.queries.EvaluateAlertRulesQuery;
import com.teralume.energycore.notifications.domain.model.queries.GetAlertRulesByUserQuery;
import com.teralume.energycore.notifications.domain.model.queries.GetAlertRuleProfilesByUserQuery;
import com.teralume.energycore.notifications.domain.model.queries.GetAlertsByUserQuery;
import com.teralume.energycore.notifications.domain.model.queries.GetNotificationPreferenceQuery;
import com.teralume.energycore.notifications.domain.model.results.RuleEvaluationResult;

import java.util.List;

public interface NotificationQueryService {
    List<Alert> handle(GetAlertsByUserQuery query);
    List<AlertRule> handle(GetAlertRulesByUserQuery query);
    List<AlertRuleProfile> handle(GetAlertRuleProfilesByUserQuery query);
    RuleEvaluationResult handle(EvaluateAlertRulesQuery query);
    NotificationPreference handle(GetNotificationPreferenceQuery query);
}
