package com.teralume.energycore.energymonitoring.application.services;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EnergySamplingSettingsService {

    private static final int DEFAULT_SECONDS = 15;
    private static final int MIN_SECONDS = 5;
    private static final int MAX_SECONDS = 3600;

    private final AtomicInteger sampleSeconds = new AtomicInteger(DEFAULT_SECONDS);

    public int getSampleSeconds() {
        return sampleSeconds.get();
    }

    public int updateSampleSeconds(int seconds) {
        int normalized = Math.max(MIN_SECONDS, Math.min(MAX_SECONDS, seconds));
        sampleSeconds.set(normalized);
        return normalized;
    }
}