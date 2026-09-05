package com.teralume.energycore.iam.application.commandservices;

import com.teralume.energycore.iam.domain.model.aggregates.User;
import com.teralume.energycore.iam.domain.model.aggregates.UserUiPreference;
import com.teralume.energycore.iam.domain.model.commands.AssignAccessProfileCommand;
import com.teralume.energycore.iam.domain.model.commands.UpdateProfileCommand;
import com.teralume.energycore.iam.domain.model.commands.UpdateUiPreferenceCommand;

public interface UserCommandService {
    User updateProfile(Long userId, UpdateProfileCommand command);

    User assignAccessProfile(AssignAccessProfileCommand command);

    UserUiPreference updateUiPreference(Long userId, UpdateUiPreferenceCommand command);

    void deleteAccount(Long userId);
}
