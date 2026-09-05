package com.teralume.energycore.shared.interfaces.rest.resources;

import java.util.List;

public record PageResource<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}