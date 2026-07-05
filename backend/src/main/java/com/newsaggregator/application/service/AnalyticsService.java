package com.newsaggregator.application.service;

import com.newsaggregator.application.dto.AnalyticsDashboardDto;
import com.newsaggregator.infrastructure.repository.ArticleAnalysisRepository;
import com.newsaggregator.infrastructure.repository.NewsArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final NewsArticleRepository newsArticleRepository;
    private final ArticleAnalysisRepository articleAnalysisRepository;

    public AnalyticsService(NewsArticleRepository newsArticleRepository, ArticleAnalysisRepository articleAnalysisRepository) {
        this.newsArticleRepository = newsArticleRepository;
        this.articleAnalysisRepository = articleAnalysisRepository;
    }

    @Transactional(readOnly = true)
    public AnalyticsDashboardDto getDashboardAnalytics() {
        long totalArticles = newsArticleRepository.count();
        long totalProcessed = articleAnalysisRepository.count();

        return AnalyticsDashboardDto.builder()
                .totalArticles(totalArticles)
                .totalProcessed(totalProcessed)
                .sentimentDistribution(mapToChartData(articleAnalysisRepository.countBySentiment()))
                .trendingTopics(mapToChartData(articleAnalysisRepository.findTopTrendingTopics()))
                .sourceDistribution(mapToChartData(newsArticleRepository.countBySource()))
                .categoryDistribution(mapToChartData(newsArticleRepository.countByCategory()))
                .credibilityAnalysis(mapToChartData(articleAnalysisRepository.countByCredibilityScoreBuckets()))
                .dailyArticleGrowth(mapToChartData(newsArticleRepository.countByPublishedDate()))
                .build();
    }

    private List<AnalyticsDashboardDto.ChartData> mapToChartData(List<Object[]> rawData) {
        return rawData.stream()
                .map(row -> new AnalyticsDashboardDto.ChartData(
                        row[0] != null ? row[0].toString() : "Unknown",
                        row[1] instanceof Number ? (Number) row[1] : 0
                ))
                .collect(Collectors.toList());
    }
}
