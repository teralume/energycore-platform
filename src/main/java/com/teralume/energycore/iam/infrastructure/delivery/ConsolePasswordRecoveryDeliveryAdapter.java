package com.teralume.energycore.iam.infrastructure.delivery;

import com.teralume.energycore.iam.application.ports.PasswordRecoveryDeliveryPort;
import com.teralume.energycore.iam.domain.model.aggregates.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class ConsolePasswordRecoveryDeliveryAdapter implements PasswordRecoveryDeliveryPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsolePasswordRecoveryDeliveryAdapter.class);

    @Value("${energycore.webapp.reset-password-url:http://localhost:4200/iam/reset-password}")
    private String resetPasswordUrl;

    @Override
    public void sendPasswordResetLink(User user, String resetToken) {
        String separator = resetPasswordUrl.contains("?") ? "&" : "?";
        String encodedToken = URLEncoder.encode(resetToken, StandardCharsets.UTF_8);
        String recoveryLink = resetPasswordUrl + separator + "token=" + encodedToken;

        LOGGER.info("Password reset link generated for {}: {}", user.getEmail(), recoveryLink);
    }
}
