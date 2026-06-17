package ua.kpi.oop.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record SubscriptionResponse(
        Long id,
        Long userId,
        int months,
        BigDecimal totalAmount,
        String status,
        List<Long> periodicalIds
) {
}
