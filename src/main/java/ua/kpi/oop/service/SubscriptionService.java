package ua.kpi.oop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.oop.dto.CreateSubscriptionRequest;
import ua.kpi.oop.dto.SubscriptionResponse;
import ua.kpi.oop.entity.*;
import ua.kpi.oop.repository.AppUserRepository;
import ua.kpi.oop.repository.PeriodicalRepository;
import ua.kpi.oop.repository.SubscriptionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final AppUserRepository appUserRepository;
    private final PeriodicalRepository periodicalRepository;

    @Transactional
    public SubscriptionResponse create(CreateSubscriptionRequest request) {
        if (request.userId() == null) {
            throw new IllegalArgumentException("User id is required");
        }
        if (request.periodicalIds() == null || request.periodicalIds().isEmpty()) {
            throw new IllegalArgumentException("At least one periodical is required");
        }
        if (request.months() <= 0) {
            throw new IllegalArgumentException("Months must be greater than zero");
        }

        AppUser user = appUserRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Periodical> periodicals = periodicalRepository.findAllById(request.periodicalIds());
        if (periodicals.size() != request.periodicalIds().size()) {
            throw new IllegalArgumentException("Some periodicals were not found");
        }

        BigDecimal oneMonthAmount = periodicals.stream()
                .map(Periodical::getMonthlyPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalAmount = oneMonthAmount.multiply(BigDecimal.valueOf(request.months()));

        Subscription subscription = Subscription.builder()
                .user(user)
                .months(request.months())
                .totalAmount(totalAmount)
                .status(SubscriptionStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        for (Periodical periodical : periodicals) {
            subscription.addItem(SubscriptionItem.builder()
                    .periodical(periodical)
                    .price(periodical.getMonthlyPrice())
                    .build());
        }

        Subscription saved = subscriptionRepository.save(subscription);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionResponse> findAll() {
        return subscriptionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private SubscriptionResponse toResponse(Subscription subscription) {
        return SubscriptionResponse.builder()
                .id(subscription.getId())
                .userId(subscription.getUser().getId())
                .months(subscription.getMonths())
                .totalAmount(subscription.getTotalAmount())
                .status(subscription.getStatus().name())
                .periodicalIds(subscription.getItems().stream()
                        .map(item -> item.getPeriodical().getId())
                        .toList())
                .build();
    }
}
