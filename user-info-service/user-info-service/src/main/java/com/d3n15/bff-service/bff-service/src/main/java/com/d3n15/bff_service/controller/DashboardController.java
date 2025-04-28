package com.d3n15.bff_service.controller;

import com.d3n15.bff_service.dto.DashboardDTO;
import com.d3n15.bff_service.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/dashboard")
@Validated
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<DashboardDTO>> getDashboard(@PathVariable String userId) {
        return dashboardService.getDashboard(userId)
                .map(ResponseEntity::ok);
    }
}