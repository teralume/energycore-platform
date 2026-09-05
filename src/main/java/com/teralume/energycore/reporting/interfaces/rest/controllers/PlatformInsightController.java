package com.teralume.energycore.reporting.interfaces.rest.controllers;

import com.teralume.energycore.reporting.application.services.PlatformInsightApplicationService;
import com.teralume.energycore.reporting.interfaces.rest.resources.PlatformSummaryResource;
import com.teralume.energycore.iam.application.security.AccessAuthorizationService;
import com.teralume.energycore.iam.domain.model.AccessPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reporting/platform")
@RequiredArgsConstructor
public class PlatformInsightController {

    private final PlatformInsightApplicationService platformInsightApplicationService;
    private final AccessAuthorizationService accessAuthorizationService;

    @GetMapping("/summary")
    public PlatformSummaryResource getPlatformSummary() {
        var summary = platformInsightApplicationService.getPlatformSummary(
                accessAuthorizationService.requirePermission(AccessPermission.VIEW_REPORTS)
        );
        return PlatformSummaryResource.from(summary);
    }
}
