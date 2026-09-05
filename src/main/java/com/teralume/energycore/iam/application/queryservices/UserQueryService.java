package com.teralume.energycore.iam.application.queryservices;

import com.teralume.energycore.iam.domain.model.aggregates.User;
import com.teralume.energycore.iam.domain.model.aggregates.UserUiPreference;

import java.util.List;

public interface UserQueryService {
    User getProfile(Long userId);

    List<User> getUsers();

    UserUiPreference getUiPreference(Long userId);
}
