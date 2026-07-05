package com.newsaggregator.infrastructure.scheduler;

import com.newsaggregator.application.service.NewsAggregationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NewsScheduler {

    private final NewsAggregationService newsAggregationService;

    public NewsScheduler(NewsAggregationService newsAggregationService) {
        this.newsAggregationService = newsAggregationService;
    }

    // Run every hour
    @Scheduled(cron = "0 0 * * * ?")
    public void scheduleNewsAggregation() {
        newsAggregationService.aggregateNews();
    }
}
