package ua.kpi.oop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.oop.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
