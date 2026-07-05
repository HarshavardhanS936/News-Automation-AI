package com.newsaggregator.application.service.analysis;

import com.newsaggregator.domain.entity.NewsArticle;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BiasService {
    
    private final Random random = new Random();
    private final String[] BIASES = {"LEFT", "CENTER", "RIGHT"};

    public String analyzeBias(NewsArticle article) {
        // TODO: Replace mock with actual AI LLM classification
        return BIASES[random.nextInt(BIASES.length)];
    }
}
