package ua.kpi.oop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.oop.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsBySubscriptionId(Long subscriptionId);
}
