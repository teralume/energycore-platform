package com.teralume.energycore.iam.application.ports;

import com.teralume.energycore.iam.domain.model.aggregates.User;

public interface PasswordRecoveryDeliveryPort {
    void sendPasswordResetLink(User user, String resetToken);
}
