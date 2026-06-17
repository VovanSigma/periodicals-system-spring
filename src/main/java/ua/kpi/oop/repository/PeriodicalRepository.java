package ua.kpi.oop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.oop.entity.Periodical;

import java.util.List;

public interface PeriodicalRepository extends JpaRepository<Periodical, Long> {
    List<Periodical> findByActiveTrueOrderByTitleAsc();
}
