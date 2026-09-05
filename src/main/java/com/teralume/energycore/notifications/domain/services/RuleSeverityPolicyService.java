package com.teralume.energycore.notifications.domain.services;

import com.teralume.energycore.notifications.domain.model.valueobjects.AlertLevel;
import org.springframework.stereotype.Service;

@Service
public class RuleSeverityPolicyService {

    public AlertLevel classify(int score) {
        int normalized = Math.max(0, Math.min(100, score));

        if (normalized >= 80) {
            return AlertLevel.CRITICAL;
        }

        if (normalized >= 55) {
            return AlertLevel.WARNING;
        }

        if (normalized >= 25) {
            return AlertLevel.INFO;
        }

        return AlertLevel.STABLE;
    }

    public String recommendedAction(AlertLevel level) {
        return switch (level) {
            case STABLE -> "Mantener la operacion actual y conservar el registro como referencia.";
            case SUCCESS -> "Registrar el logro y sostener el comportamiento actual.";
            case INFO -> "Revisar el contexto cuando sea oportuno.";
            case WARNING -> "Revisar preventivamente antes de que el evento escale.";
            case CRITICAL -> "Atender de inmediato y validar el origen operativo.";
        };
    }
}
