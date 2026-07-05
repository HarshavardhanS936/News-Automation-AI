package com.newsaggregator.application.service.analysis;

import com.newsaggregator.domain.entity.NewsArticle;
import org.springframework.stereotype.Service;

@Service
public class ReadingTimeService {

    private static final int WORDS_PER_MINUTE = 200;

    public Integer calculateReadingTime(NewsArticle article) {
        if (article.getContent() == null || article.getContent().isEmpty()) {
            return 1;
        }
        
        String[] words = article.getContent().split("\\s+");
        int count = words.length;
        
        int minutes = (int) Math.ceil((double) count / WORDS_PER_MINUTE);
        return Math.max(1, minutes);
    }
}
