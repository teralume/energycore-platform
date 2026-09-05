package com.teralume.energycore.iam.interfaces.rest.controllers;

import com.teralume.energycore.iam.application.commandservices.UserCommandService;
import com.teralume.energycore.iam.application.queryservices.UserQueryService;
import com.teralume.energycore.iam.application.security.AccessAuthorizationService;
import com.teralume.energycore.iam.domain.model.AccessPermission;
import com.teralume.energycore.iam.domain.model.commands.AssignAccessProfileCommand;
import com.teralume.energycore.iam.domain.model.commands.UpdateUiPreferenceCommand;
import com.teralume.energycore.iam.interfaces.rest.resources.AssignAccessProfileResource;
import com.teralume.energycore.iam.interfaces.rest.resources.UpdateProfileResource;
import com.teralume.energycore.iam.interfaces.rest.resources.UpdateUiPreferenceResource;
import com.teralume.energycore.iam.interfaces.rest.resources.UserUiPreferenceResource;
import com.teralume.energycore.iam.interfaces.rest.resources.UserResource;
import com.teralume.energycore.iam.interfaces.rest.transform.UpdateProfileCommandFromResourceAssembler;
import com.teralume.energycore.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.teralume.energycore.shared.infrastructure.security.CurrentUserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final CurrentUserProvider currentUserProvider;
    private final AccessAuthorizationService accessAuthorizationService;

    @GetMapping
    public List<UserResource> getUsers() {
        accessAuthorizationService.requirePermission(AccessPermission.MANAGE_ACCESS);

        return userQueryService.getUsers()
                .stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @GetMapping("/me")
    public UserResource getCurrentUserProfile() {
        return UserResourceFromEntityAssembler.toResourceFromEntity(
                userQueryService.getProfile(currentUserProvider.getCurrentUserId())
        );
    }

    @PutMapping("/me")
    public UserResource updateCurrentUserProfile(@Valid @RequestBody UpdateProfileResource request) {
        return UserResourceFromEntityAssembler.toResourceFromEntity(
                userCommandService.updateProfile(
                        currentUserProvider.getCurrentUserId(),
                        UpdateProfileCommandFromResourceAssembler.toCommandFromResource(request)
                )
        );
    }

    @DeleteMapping("/me")
    public void deleteCurrentUserAccount() {
        userCommandService.deleteAccount(currentUserProvider.getCurrentUserId());
    }

    @GetMapping("/me/ui-preferences")
    public UserUiPreferenceResource getCurrentUserUiPreference() {
        return UserUiPreferenceResource.from(
                userQueryService.getUiPreference(currentUserProvider.getCurrentUserId())
        );
    }

    @PutMapping("/me/ui-preferences")
    public UserUiPreferenceResource updateCurrentUserUiPreference(@RequestBody UpdateUiPreferenceResource request) {
        return UserUiPreferenceResource.from(
                userCommandService.updateUiPreference(
                        currentUserProvider.getCurrentUserId(),
                        new UpdateUiPreferenceCommand(request.language(), request.theme())
                )
        );
    }

    @GetMapping("/{userId}/profile")
    public UserResource getProfile(@PathVariable Long userId) {
        requireSelfOrAccessManager(userId);
        return UserResourceFromEntityAssembler.toResourceFromEntity(userQueryService.getProfile(userId));
    }

    @PutMapping("/{userId}/profile")
    public UserResource updateProfile(@PathVariable Long userId, @Valid @RequestBody UpdateProfileResource request) {
        requireSelfOrAccessManager(userId);
        return UserResourceFromEntityAssembler.toResourceFromEntity(
                userCommandService.updateProfile(
                        userId,
                        UpdateProfileCommandFromResourceAssembler.toCommandFromResource(request)
                )
        );
    }

    @PatchMapping("/{userId}/access-profile")
    public UserResource assignAccessProfile(
            @PathVariable Long userId,
            @Valid @RequestBody AssignAccessProfileResource request
    ) {
        return UserResourceFromEntityAssembler.toResourceFromEntity(
                userCommandService.assignAccessProfile(new AssignAccessProfileCommand(
                        currentUserProvider.getCurrentUserId(),
                        userId,
                        request.accessProfileId()
                ))
        );
    }

    @DeleteMapping("/{userId}")
    public void deleteAccount(@PathVariable Long userId) {
        requireSelfOrAccessManager(userId);
        userCommandService.deleteAccount(userId);
    }

    private void requireSelfOrAccessManager(Long userId) {
        accessAuthorizationService.requireSelfOrPermission(userId, AccessPermission.MANAGE_ACCESS);
    }
}
