package com.teralume.energycore.devicecontrol.interfaces.rest.resources;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

final class CsvIds {
    private CsvIds() {
    }

    static List<Long> parse(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }

        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(item -> !item.isBlank())
                .map(CsvIds::parseLong)
                .filter(Objects::nonNull)
                .toList();
    }

    private static Long parseLong(String value) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
