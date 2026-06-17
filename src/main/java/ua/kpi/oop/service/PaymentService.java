package ua.kpi.oop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.oop.dto.PaymentRequest;
import ua.kpi.oop.dto.PaymentResponse;
import ua.kpi.oop.entity.Payment;
import ua.kpi.oop.entity.Subscription;
import ua.kpi.oop.entity.SubscriptionStatus;
import ua.kpi.oop.repository.PaymentRepository;
import ua.kpi.oop.repository.SubscriptionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public PaymentResponse pay(PaymentRequest request) {
        if (request.subscriptionId() == null) {
            throw new IllegalArgumentException("Subscription id is required");
        }
        if (request.method() == null || request.method().isBlank()) {
            throw new IllegalArgumentException("Payment method is required");
        }

        Subscription subscription = subscriptionRepository.findById(request.subscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        if (subscription.getStatus() == SubscriptionStatus.PAID || paymentRepository.existsBySubscriptionId(subscription.getId())) {
            throw new IllegalArgumentException("Subscription is already paid");
        }

        Payment payment = Payment.builder()
                .subscription(subscription)
                .amount(subscription.getTotalAmount())
                .method(request.method())
                .paidAt(LocalDateTime.now())
                .build();

        subscription.setStatus(SubscriptionStatus.PAID);
        Payment saved = paymentRepository.save(payment);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .subscriptionId(payment.getSubscription().getId())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .build();
    }
}
