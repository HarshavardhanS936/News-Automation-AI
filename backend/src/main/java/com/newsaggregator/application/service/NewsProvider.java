package com.newsaggregator.application.service;

import com.newsaggregator.domain.entity.NewsArticle;
import java.util.List;

public interface NewsProvider {
    List<NewsArticle> fetchNews();
    String getProviderName();
}
