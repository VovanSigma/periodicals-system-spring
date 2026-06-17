package ua.kpi.oop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.oop.dto.CreatePeriodicalRequest;
import ua.kpi.oop.dto.PeriodicalDto;
import ua.kpi.oop.entity.Periodical;
import ua.kpi.oop.mapper.PeriodicalMapper;
import ua.kpi.oop.repository.PeriodicalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeriodicalService {
    private final PeriodicalRepository periodicalRepository;
    private final PeriodicalMapper periodicalMapper;

    @Transactional(readOnly = true)
    public List<PeriodicalDto> getCatalog() {
        return periodicalRepository.findByActiveTrueOrderByTitleAsc()
                .stream()
                .map(periodicalMapper::toDto)
                .toList();
    }

    @Transactional
    public PeriodicalDto create(CreatePeriodicalRequest request) {
        if (request.title() == null || request.title().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (request.monthlyPrice() == null || request.monthlyPrice().signum() < 0) {
            throw new IllegalArgumentException("Monthly price must be positive");
        }
        Periodical saved = periodicalRepository.save(periodicalMapper.toEntity(request));
        return periodicalMapper.toDto(saved);
    }

    @Transactional
    public void deactivate(Long id) {
        Periodical periodical = periodicalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Periodical not found"));
        periodical.setActive(false);
    }
}
