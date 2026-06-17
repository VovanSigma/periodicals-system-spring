package ua.kpi.oop.dto;

public record PaymentRequest(
        Long subscriptionId,
        String method
) {
}
