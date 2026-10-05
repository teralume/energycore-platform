package com.teralume.energycore.acceptance;

import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class TestContext {
    public String jwtToken;
    public String lastAuthEmail;
    public HttpResponse<String> lastResponse;
    public Long lastCreatedId;
    public boolean iamScenario;
    public final Map<String, Long> ids = new HashMap<>();

    public void resetScenario() {
        jwtToken = null;
        lastAuthEmail = null;
        lastResponse = null;
        lastCreatedId = null;
        iamScenario = false;
        ids.clear();
    }
}
