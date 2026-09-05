package com.teralume.energycore.reporting.domain.services;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReportFormatPolicyService {

    private static final Set<String> ALLOWED_FORMATS = Set.of(
            "CSV",
            "PDF",
            "JSON"
    );

    public void validateFormat(String format) {
        if (format == null || !ALLOWED_FORMATS.contains(format.toUpperCase())) {
            throw new IllegalArgumentException("Report format is invalid.");
        }
    }

    public boolean isCsv(String format) {
        return "CSV".equalsIgnoreCase(format);
    }

    public boolean isPdf(String format) {
        return "PDF".equalsIgnoreCase(format);
    }

    public boolean isJson(String format) {
        return "JSON".equalsIgnoreCase(format);
    }
}