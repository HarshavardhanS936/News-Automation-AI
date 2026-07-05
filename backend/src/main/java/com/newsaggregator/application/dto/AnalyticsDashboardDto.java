package com.newsaggregator.application.dto;

import java.util.List;

public class AnalyticsDashboardDto {
    private long totalArticles;
    private long totalProcessed;
    private List<ChartData> sentimentDistribution;
    private List<ChartData> trendingTopics;
    private List<ChartData> sourceDistribution;
    private List<ChartData> categoryDistribution;
    private List<ChartData> credibilityAnalysis;
    private List<ChartData> dailyArticleGrowth;

    public AnalyticsDashboardDto() {}

    public AnalyticsDashboardDto(long totalArticles, long totalProcessed, List<ChartData> sentimentDistribution, List<ChartData> trendingTopics, List<ChartData> sourceDistribution, List<ChartData> categoryDistribution, List<ChartData> credibilityAnalysis, List<ChartData> dailyArticleGrowth) {
        this.totalArticles = totalArticles;
        this.totalProcessed = totalProcessed;
        this.sentimentDistribution = sentimentDistribution;
        this.trendingTopics = trendingTopics;
        this.sourceDistribution = sourceDistribution;
        this.categoryDistribution = categoryDistribution;
        this.credibilityAnalysis = credibilityAnalysis;
        this.dailyArticleGrowth = dailyArticleGrowth;
    }

    public long getTotalArticles() { return totalArticles; }
    public void setTotalArticles(long totalArticles) { this.totalArticles = totalArticles; }
    public long getTotalProcessed() { return totalProcessed; }
    public void setTotalProcessed(long totalProcessed) { this.totalProcessed = totalProcessed; }
    public List<ChartData> getSentimentDistribution() { return sentimentDistribution; }
    public void setSentimentDistribution(List<ChartData> sentimentDistribution) { this.sentimentDistribution = sentimentDistribution; }
    public List<ChartData> getTrendingTopics() { return trendingTopics; }
    public void setTrendingTopics(List<ChartData> trendingTopics) { this.trendingTopics = trendingTopics; }
    public List<ChartData> getSourceDistribution() { return sourceDistribution; }
    public void setSourceDistribution(List<ChartData> sourceDistribution) { this.sourceDistribution = sourceDistribution; }
    public List<ChartData> getCategoryDistribution() { return categoryDistribution; }
    public void setCategoryDistribution(List<ChartData> categoryDistribution) { this.categoryDistribution = categoryDistribution; }
    public List<ChartData> getCredibilityAnalysis() { return credibilityAnalysis; }
    public void setCredibilityAnalysis(List<ChartData> credibilityAnalysis) { this.credibilityAnalysis = credibilityAnalysis; }
    public List<ChartData> getDailyArticleGrowth() { return dailyArticleGrowth; }
    public void setDailyArticleGrowth(List<ChartData> dailyArticleGrowth) { this.dailyArticleGrowth = dailyArticleGrowth; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long totalArticles;
        private long totalProcessed;
        private List<ChartData> sentimentDistribution;
        private List<ChartData> trendingTopics;
        private List<ChartData> sourceDistribution;
        private List<ChartData> categoryDistribution;
        private List<ChartData> credibilityAnalysis;
        private List<ChartData> dailyArticleGrowth;

        public Builder totalArticles(long totalArticles) { this.totalArticles = totalArticles; return this; }
        public Builder totalProcessed(long totalProcessed) { this.totalProcessed = totalProcessed; return this; }
        public Builder sentimentDistribution(List<ChartData> sentimentDistribution) { this.sentimentDistribution = sentimentDistribution; return this; }
        public Builder trendingTopics(List<ChartData> trendingTopics) { this.trendingTopics = trendingTopics; return this; }
        public Builder sourceDistribution(List<ChartData> sourceDistribution) { this.sourceDistribution = sourceDistribution; return this; }
        public Builder categoryDistribution(List<ChartData> categoryDistribution) { this.categoryDistribution = categoryDistribution; return this; }
        public Builder credibilityAnalysis(List<ChartData> credibilityAnalysis) { this.credibilityAnalysis = credibilityAnalysis; return this; }
        public Builder dailyArticleGrowth(List<ChartData> dailyArticleGrowth) { this.dailyArticleGrowth = dailyArticleGrowth; return this; }
        public AnalyticsDashboardDto build() { return new AnalyticsDashboardDto(totalArticles, totalProcessed, sentimentDistribution, trendingTopics, sourceDistribution, categoryDistribution, credibilityAnalysis, dailyArticleGrowth); }
    }

    public static class ChartData {
        private String name;
        private Number value;

        public ChartData() {}

        public ChartData(String name, Number value) {
            this.name = name;
            this.value = value;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Number getValue() { return value; }
        public void setValue(Number value) { this.value = value; }
    }
}
