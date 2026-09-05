package com.teralume.energycore.iam.application.commandservices;

import com.teralume.energycore.iam.application.results.AuthenticationResult;
import com.teralume.energycore.iam.domain.model.commands.RecoverPasswordCommand;
import com.teralume.energycore.iam.domain.model.commands.ResetPasswordCommand;
import com.teralume.energycore.iam.domain.model.commands.SignInCommand;
import com.teralume.energycore.iam.domain.model.commands.SignUpCommand;

public interface AuthCommandService {
    AuthenticationResult signUp(SignUpCommand command);

    AuthenticationResult signIn(SignInCommand command);

    void signOut();

    void recoverPassword(RecoverPasswordCommand command);

    void resetPassword(ResetPasswordCommand command);
}
