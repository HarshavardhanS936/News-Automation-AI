package com.newsaggregator.application.service.analysis;

import com.newsaggregator.domain.entity.NewsArticle;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SentimentService {
    
    private final Random random = new Random();
    private final String[] SENTIMENTS = {"POSITIVE", "NEUTRAL", "NEGATIVE"};

    public String analyzeSentiment(NewsArticle article) {
        // TODO: Replace heuristic/random mock with actual AI LLM call
        String content = article.getContent() != null ? article.getContent().toLowerCase() : "";
        if (content.contains("good") || content.contains("success") || content.contains("breakthrough")) {
            return "POSITIVE";
        }
        if (content.contains("bad") || content.contains("fail") || content.contains("tragedy")) {
            return "NEGATIVE";
        }
        
        return SENTIMENTS[random.nextInt(SENTIMENTS.length)];
    }
}
