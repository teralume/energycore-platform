package com.teralume.energycore.iam.application.queryservices;

import com.teralume.energycore.iam.application.results.AccessProfileDetails;

import java.util.List;

public interface AccessProfileQueryService {
    List<AccessProfileDetails> getAccessProfiles();
}
