package ua.kpi.oop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.oop.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
