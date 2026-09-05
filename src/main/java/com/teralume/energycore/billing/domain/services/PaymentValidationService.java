package com.teralume.energycore.billing.domain.services;

import org.springframework.stereotype.Service;

@Service
public class PaymentValidationService {

    public boolean isValidHolderName(String holderName) {
        return holderName != null && holderName.trim().length() >= 3;
    }

    public boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null) {
            return false;
        }

        String sanitized = cardNumber.replace(" ", "");
        return sanitized.length() >= 13
                && sanitized.length() <= 19
                && sanitized.matches("\\d+");
    }

    public boolean isValidExpirationDate(String expirationDate) {
        return expirationDate != null && expirationDate.trim().matches("^(0[1-9]|1[0-2])/\\d{2}$");
    }

    public boolean isValidCvv(String cvv) {
        return cvv != null && cvv.trim().matches("\\d{3}");
    }
}
