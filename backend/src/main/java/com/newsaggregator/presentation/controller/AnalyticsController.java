package com.newsaggregator.presentation.controller;

import com.newsaggregator.application.dto.AnalyticsDashboardDto;
import com.newsaggregator.application.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<AnalyticsDashboardDto> getDashboard() {
        return ResponseEntity.ok(analyticsService.getDashboardAnalytics());
    }
}
