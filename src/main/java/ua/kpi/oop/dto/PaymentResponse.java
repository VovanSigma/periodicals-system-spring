package ua.kpi.oop.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentResponse(
        Long id,
        Long subscriptionId,
        BigDecimal amount,
        String method
) {
}
