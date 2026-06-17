package ua.kpi.oop.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PeriodicalDto(
        Long id,
        String title,
        String description,
        String category,
        BigDecimal monthlyPrice,
        boolean active
) {
}
