package com.teralume.energycore.billing.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record CheckoutSubscriptionResource(
        @NotBlank String planCode,
        @NotBlank String holderName,
        @NotBlank String cardNumber,
        @NotBlank String expirationDate,
        @NotBlank String cvv
) {
}
