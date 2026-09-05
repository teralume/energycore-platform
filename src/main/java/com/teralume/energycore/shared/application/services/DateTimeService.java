package com.teralume.energycore.shared.application.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DateTimeService {

    public LocalDate today() {
        return LocalDate.now();
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}