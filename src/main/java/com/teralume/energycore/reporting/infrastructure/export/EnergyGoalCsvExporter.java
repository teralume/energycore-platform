package com.teralume.energycore.reporting.infrastructure.export;

import com.teralume.energycore.reporting.domain.model.aggregates.EnergyGoal;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class EnergyGoalCsvExporter {

    public byte[] export(List<EnergyGoal> goals) {
        StringBuilder csv = new StringBuilder();

        csv.append("id,userId,title,targetKilowattHours,currentKilowattHours,deadline,status,scopeType,scopeId,scopeName,activeFrom,activeTo")
                .append(System.lineSeparator());

        if (goals != null) {
            for (EnergyGoal goal : goals) {
                csv.append(goal.getId()).append(",")
                        .append(goal.getUserId()).append(",")
                        .append(sanitize(goal.getTitle())).append(",")
                        .append(goal.getTargetKilowattHours()).append(",")
                        .append(goal.getCurrentKilowattHours()).append(",")
                        .append(goal.getDeadline()).append(",")
                        .append(goal.getStatus()).append(",")
                        .append(goal.getScopeType()).append(",")
                        .append(goal.getScopeId()).append(",")
                        .append(sanitize(goal.getScopeName())).append(",")
                        .append(goal.getActiveFrom()).append(",")
                        .append(goal.getActiveTo())
                        .append(System.lineSeparator());
            }
        }

        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }

    private String sanitize(String value) {
        if (value == null) {
            return "";
        }

        return value.replace(",", " ");
    }
}
