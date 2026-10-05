package com.teralume.energycore.acceptance;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.springframework.beans.factory.annotation.Autowired;

public class AcceptanceHooks {

    @Autowired
    private TestContext context;

    @Before
    public void resetScenarioContext(Scenario scenario) {
        context.resetScenario();
        context.iamScenario = scenario.getSourceTagNames().contains("@iam");
    }
}
