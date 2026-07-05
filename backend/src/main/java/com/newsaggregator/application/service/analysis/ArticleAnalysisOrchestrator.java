package com.newsaggregator.application.service.analysis;

import com.newsaggregator.domain.entity.ArticleAnalysis;
import com.newsaggregator.domain.entity.NewsArticle;
import com.newsaggregator.infrastructure.repository.ArticleAnalysisRepository;
import com.newsaggregator.application.service.analysis.GeminiAiService.AiAnalysisResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleAnalysisOrchestrator {

    private final SentimentService sentimentService;
    private final BiasService biasService;
    private final CredibilityService credibilityService;
    private final TrendingService trendingService;
    private final LanguageService languageService;
    private final ReadingTimeService readingTimeService;
    private final ArticleAnalysisRepository articleAnalysisRepository;

    public ArticleAnalysisOrchestrator(
            SentimentService sentimentService,
            BiasService biasService,
            CredibilityService credibilityService,
            TrendingService trendingService,
            LanguageService languageService,
            ReadingTimeService readingTimeService,
            ArticleAnalysisRepository articleAnalysisRepository) {
        this.sentimentService = sentimentService;
        this.biasService = biasService;
        this.credibilityService = credibilityService;
        this.trendingService = trendingService;
        this.languageService = languageService;
        this.readingTimeService = readingTimeService;
        this.articleAnalysisRepository = articleAnalysisRepository;
    }

    @Transactional
    public ArticleAnalysis analyze(NewsArticle article) {
        ArticleAnalysis analysis = ArticleAnalysis.builder()
                .article(article)
                .sentiment(sentimentService.analyzeSentiment(article))
                .bias(biasService.analyzeBias(article))
                .credibilityScore(credibilityService.calculateCredibility(article))
                .trendingScore(trendingService.calculateTrendingScore(article))
                .language(languageService.detectLanguage(article))
                .readingTimeMinutes(readingTimeService.calculateReadingTime(article))
                .build();

        return articleAnalysisRepository.save(analysis);
    }

    @Transactional
    public ArticleAnalysis analyzeWithAi(NewsArticle article, AiAnalysisResult aiResult) {
        ArticleAnalysis analysis = ArticleAnalysis.builder()
                .article(article)
                .sentiment(aiResult.getSentiment() != null ? aiResult.getSentiment() : sentimentService.analyzeSentiment(article))
                .bias(biasService.analyzeBias(article))
                .credibilityScore(aiResult.getCredibilityScore() != null ? aiResult.getCredibilityScore() : credibilityService.calculateCredibility(article))
                .trendingScore(trendingService.calculateTrendingScore(article))
                .language(languageService.detectLanguage(article))
                .readingTimeMinutes(readingTimeService.calculateReadingTime(article))
                .build();

        return articleAnalysisRepository.save(analysis);
    }
}
