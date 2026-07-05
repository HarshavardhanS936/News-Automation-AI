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
public class GuardianApiProvider implements NewsProvider {

    @Value("${guardian.key:test}") // test is a valid public key for Guardian
    private String apiKey;

    private final SourceRepository sourceRepository;
    private final WebClient webClient;

    public GuardianApiProvider(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
        this.webClient = WebClient.create("https://content.guardianapis.com");
    }

    @Override
    public List<NewsArticle> fetchNews() {
        List<NewsArticle> articles = new ArrayList<>();
        try {
            Map response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search")
                            .queryParam("show-fields", "bodyText,thumbnail")
                            .queryParam("api-key", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null && response.containsKey("response")) {
                Map<String, Object> resObj = (Map<String, Object>) response.get("response");
                List<Map<String, Object>> results = (List<Map<String, Object>>) resObj.get("results");

                Source apiSource = sourceRepository.findByName("The Guardian")
                        .orElseGet(() -> sourceRepository.save(Source.builder().name("The Guardian").url("https://www.theguardian.com").build()));

                for (Map<String, Object> item : results) {
                    Map<String, String> fields = (Map<String, String>) item.get("fields");
                    
                    NewsArticle article = NewsArticle.builder()
                            .title((String) item.get("webTitle"))
                            .url((String) item.get("webUrl"))
                            .publishedAt(ZonedDateTime.parse((String) item.get("webPublicationDate")).toLocalDateTime())
                            .source(apiSource)
                            .build();

                    if (fields != null) {
                        article.setContent(fields.get("bodyText"));
                        article.setImageUrl(fields.get("thumbnail"));
                    }
                    if (article.getContent() == null) article.setContent(article.getTitle());
                    articles.add(article);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching from The Guardian: " + e.getMessage());
        }
        return articles;
    }

    @Override
    public String getProviderName() {
        return "TheGuardian";
    }
}
