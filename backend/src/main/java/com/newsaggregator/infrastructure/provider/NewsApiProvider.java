package com.newsaggregator.infrastructure.provider;

import com.newsaggregator.application.service.NewsProvider;
import com.newsaggregator.domain.entity.NewsArticle;
import com.newsaggregator.domain.entity.Source;
import com.newsaggregator.infrastructure.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NewsApiProvider implements NewsProvider {

    @Value("${newsapi.key:default_newsapi_key}")
    private String apiKey;

    private final SourceRepository sourceRepository;
    private final WebClient webClient;

    public NewsApiProvider(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
        this.webClient = WebClient.create("https://newsapi.org/v2");
    }

    @Override
    public List<NewsArticle> fetchNews() {
        List<NewsArticle> articles = new ArrayList<>();
        try {
            Map response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/top-headlines")
                            .queryParam("country", "us")
                            .queryParam("apiKey", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null && "ok".equals(response.get("status"))) {
                List<Map<String, Object>> articlesData = (List<Map<String, Object>>) response.get("articles");
                
                Source apiSource = sourceRepository.findByName("NewsAPI")
                        .orElseGet(() -> sourceRepository.save(Source.builder().name("NewsAPI").url("https://newsapi.org").build()));

                for (Map<String, Object> item : articlesData) {
                    if (item.get("url") != null && item.get("title") != null) {
                        NewsArticle article = NewsArticle.builder()
                                .title((String) item.get("title"))
                                .content(item.get("content") != null ? (String) item.get("content") : (String) item.get("description"))
                                .url((String) item.get("url"))
                                .imageUrl((String) item.get("urlToImage"))
                                .publishedAt(item.get("publishedAt") != null ? ZonedDateTime.parse((String) item.get("publishedAt")).toLocalDateTime() : null)
                                .source(apiSource)
                                .build();
                        if (article.getContent() == null) article.setContent(article.getTitle());
                        articles.add(article);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching from NewsAPI: " + e.getMessage());
        }
        return articles;
    }

    @Override
    public String getProviderName() {
        return "NewsAPI";
    }
}
