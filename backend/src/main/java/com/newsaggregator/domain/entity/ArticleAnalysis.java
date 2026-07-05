package com.newsaggregator.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "article_analysis")
public class ArticleAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "article_id", nullable = false, unique = true)
    private NewsArticle article;

    @Column(length = 50)
    private String sentiment;

    @Column(length = 50)
    private String bias;

    private Double credibilityScore;

    private Integer readingTimeMinutes;

    @Column(length = 10)
    private String language;

    private Double trendingScore;

    public ArticleAnalysis() {}
    public ArticleAnalysis(Long id, NewsArticle article, String sentiment, String bias, Double credibilityScore, Integer readingTimeMinutes, String language, Double trendingScore) {
        this.id = id; this.article = article; this.sentiment = sentiment; this.bias = bias; this.credibilityScore = credibilityScore; this.readingTimeMinutes = readingTimeMinutes; this.language = language; this.trendingScore = trendingScore;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public NewsArticle getArticle() { return article; }
    public void setArticle(NewsArticle article) { this.article = article; }
    public String getSentiment() { return sentiment; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; }
    public String getBias() { return bias; }
    public void setBias(String bias) { this.bias = bias; }
    public Double getCredibilityScore() { return credibilityScore; }
    public void setCredibilityScore(Double credibilityScore) { this.credibilityScore = credibilityScore; }
    public Integer getReadingTimeMinutes() { return readingTimeMinutes; }
    public void setReadingTimeMinutes(Integer readingTimeMinutes) { this.readingTimeMinutes = readingTimeMinutes; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public Double getTrendingScore() { return trendingScore; }
    public void setTrendingScore(Double trendingScore) { this.trendingScore = trendingScore; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id;
        private NewsArticle article;
        private String sentiment;
        private String bias;
        private Double credibilityScore;
        private Integer readingTimeMinutes;
        private String language;
        private Double trendingScore;
        public Builder id(Long id) { this.id = id; return this; }
        public Builder article(NewsArticle article) { this.article = article; return this; }
        public Builder sentiment(String sentiment) { this.sentiment = sentiment; return this; }
        public Builder bias(String bias) { this.bias = bias; return this; }
        public Builder credibilityScore(Double credibilityScore) { this.credibilityScore = credibilityScore; return this; }
        public Builder readingTimeMinutes(Integer readingTimeMinutes) { this.readingTimeMinutes = readingTimeMinutes; return this; }
        public Builder language(String language) { this.language = language; return this; }
        public Builder trendingScore(Double trendingScore) { this.trendingScore = trendingScore; return this; }
        public ArticleAnalysis build() { return new ArticleAnalysis(id, article, sentiment, bias, credibilityScore, readingTimeMinutes, language, trendingScore); }
    }
}
