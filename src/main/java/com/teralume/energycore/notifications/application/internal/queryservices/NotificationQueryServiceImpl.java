package com.teralume.energycore.notifications.application.internal.queryservices;

import com.teralume.energycore.notifications.application.queryservices.NotificationQueryService;
import com.teralume.energycore.notifications.application.services.NotificationApplicationService;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationQueryServiceImpl implements NotificationQueryService {
    private final NotificationApplicationService applicationService;

    @Override
    public List<Alert> handle(GetAlertsByUserQuery query) {
        return applicationService.getAlerts(query.userId());
    }

    @Override
    public List<AlertRule> handle(GetAlertRulesByUserQuery query) {
        return applicationService.getRules(query.userId());
    }

    @Override
    public List<AlertRuleProfile> handle(GetAlertRuleProfilesByUserQuery query) {
        return applicationService.getRuleProfiles(query.userId());
    }

    @Override
    public RuleEvaluationResult handle(EvaluateAlertRulesQuery query) {
        return applicationService.evaluateRules(query.userId(), query.scopeType(), query.scopeId(), query.observedValue());
    }

    @Override
    public NotificationPreference handle(GetNotificationPreferenceQuery query) {
        return applicationService.getPreference(query.userId());
    }
}
