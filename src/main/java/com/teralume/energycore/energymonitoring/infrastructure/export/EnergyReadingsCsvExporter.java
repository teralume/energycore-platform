package com.teralume.energycore.energymonitoring.infrastructure.export;

import com.teralume.energycore.energymonitoring.domain.model.aggregates.EnergyReading;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class EnergyReadingsCsvExporter {

    public byte[] export(List<EnergyReading> readings) {
        StringBuilder csv = new StringBuilder();

        csv.append("id,userId,deviceId,deviceName,watts,recordedAt")
                .append(System.lineSeparator());

        if (readings != null) {
            for (EnergyReading reading : readings) {
                csv.append(reading.getId()).append(",")
                        .append(reading.getUserId()).append(",")
                        .append(reading.getDeviceId()).append(",")
                        .append(sanitize(reading.getDeviceName())).append(",")
                        .append(reading.getWatts()).append(",")
                        .append(reading.getRecordedAt())
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