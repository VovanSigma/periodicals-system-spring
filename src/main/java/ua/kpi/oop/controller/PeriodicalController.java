package ua.kpi.oop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.kpi.oop.dto.CreatePeriodicalRequest;
import ua.kpi.oop.dto.PeriodicalDto;
import ua.kpi.oop.service.PeriodicalService;

import java.util.List;

@RestController
@RequestMapping("/api/periodicals")
@RequiredArgsConstructor
public class PeriodicalController {
    private final PeriodicalService periodicalService;

    @GetMapping
    public List<PeriodicalDto> getCatalog() {
        return periodicalService.getCatalog();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasRole('ADMIN')")
    public PeriodicalDto create(@RequestBody CreatePeriodicalRequest request) {
        return periodicalService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasRole('ADMIN')")
    public void deactivate(@PathVariable Long id) {
        periodicalService.deactivate(id);
    }
}
