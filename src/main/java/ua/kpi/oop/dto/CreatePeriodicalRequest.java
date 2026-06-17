package ua.kpi.oop.dto;

import java.math.BigDecimal;

public record CreatePeriodicalRequest(
        String title,
        String description,
        String category,
        BigDecimal monthlyPrice
) {
}
