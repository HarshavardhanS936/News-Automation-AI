package com.newsaggregator.application.service.analysis;

import com.newsaggregator.domain.entity.NewsArticle;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

    public String detectLanguage(NewsArticle article) {
        // TODO: Replace with real language detection (e.g. Apache Tika, CLD3, or LLM)
        // Assume English for now
        return "en";
    }
}
