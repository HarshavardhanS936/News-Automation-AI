package com.newsaggregator.application.service;

import com.newsaggregator.application.service.analysis.ArticleAnalysisOrchestrator;
import com.newsaggregator.application.service.analysis.CategorizationService;
import com.newsaggregator.domain.entity.NewsArticle;
import com.newsaggregator.infrastructure.repository.NewsArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

@Service
public class NewsAggregationService implements CommandLineRunner {

    private final List<NewsProvider> newsProviders;
    private final NewsArticleRepository newsArticleRepository;
    private final ArticleAnalysisOrchestrator analysisOrchestrator;
    private final CategorizationService categorizationService;
    private final com.newsaggregator.application.service.analysis.GeminiAiService geminiAiService;

    public NewsAggregationService(List<NewsProvider> newsProviders, 
                                  NewsArticleRepository newsArticleRepository,
                                  ArticleAnalysisOrchestrator analysisOrchestrator,
                                  CategorizationService categorizationService,
                                  com.newsaggregator.application.service.analysis.GeminiAiService geminiAiService) {
        this.newsProviders = newsProviders;
        this.newsArticleRepository = newsArticleRepository;
        this.analysisOrchestrator = analysisOrchestrator;
        this.categorizationService = categorizationService;
        this.geminiAiService = geminiAiService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("Checking for uncategorized articles...");
        List<NewsArticle> articles = newsArticleRepository.findAll();
        int count = 0;
        for (NewsArticle article : articles) {
            if (article.getCategories() == null || article.getCategories().isEmpty()) {
                categorizationService.categorize(article);
                newsArticleRepository.save(article);
                count++;
            }
        }
        if (count > 0) {
            System.out.println("Retroactively categorized " + count + " articles.");
        }
    }

    @Transactional
    public void aggregateNews() {
        System.out.println("Starting news aggregation...");
        for (NewsProvider provider : newsProviders) {
            System.out.println("Fetching from " + provider.getProviderName());
            List<NewsArticle> articles = provider.fetchNews();
            int savedCount = 0;
            for (NewsArticle article : articles) {
                if (!newsArticleRepository.existsByUrl(article.getUrl())) {
                    try {
                        com.newsaggregator.application.service.analysis.GeminiAiService.AiAnalysisResult aiResult = 
                            geminiAiService.analyzeArticle(article.getTitle(), article.getContent());

                        categorizationService.categorizeWithAi(article, aiResult.getCategory());
                        NewsArticle savedArticle = newsArticleRepository.save(article);
                        analysisOrchestrator.analyzeWithAi(savedArticle, aiResult);
                        savedCount++;
                    } catch (Exception e) {
                        System.err.println("Failed to save or analyze article: " + article.getUrl());
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Saved and analyzed " + savedCount + " new articles from " + provider.getProviderName());
        }
        System.out.println("News aggregation complete.");
    }
}
