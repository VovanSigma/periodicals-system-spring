package ua.kpi.oop.dto;

import java.util.List;

public record CreateSubscriptionRequest(
        Long userId,
        List<Long> periodicalIds,
        int months
) {
}
