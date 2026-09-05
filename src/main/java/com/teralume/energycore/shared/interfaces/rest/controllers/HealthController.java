package com.teralume.energycore.shared.interfaces.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/")
    public Map<String, String> root() {
        return Map.of(
                "status", "ok",
                "service", "EnergyCore Platform API"
        );
    }

    @GetMapping({"/health", "/api/v1/health"})
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
