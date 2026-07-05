package com.newsaggregator.application.service.analysis;

import com.newsaggregator.domain.entity.NewsArticle;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TrendingService {

    private final Random random = new Random();

    public Double calculateTrendingScore(NewsArticle article) {
        // TODO: Replace mock with actual trending calculation (e.g. social media velocity)
        // Return a score between 0.0 and 100.0
        return random.nextDouble() * 100.0;
    }
}
