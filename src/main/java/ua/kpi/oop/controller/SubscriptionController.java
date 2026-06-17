package ua.kpi.oop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.kpi.oop.dto.CreateSubscriptionRequest;
import ua.kpi.oop.dto.SubscriptionResponse;
import ua.kpi.oop.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping
    public List<SubscriptionResponse> findAll() {
        return subscriptionService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasRole('READER')")
    public SubscriptionResponse create(@RequestBody CreateSubscriptionRequest request) {
        return subscriptionService.create(request);
    }
}
