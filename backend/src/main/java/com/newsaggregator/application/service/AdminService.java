package com.newsaggregator.application.service;

import com.newsaggregator.domain.entity.NewsArticle;
import com.newsaggregator.domain.entity.User;
import com.newsaggregator.infrastructure.repository.NewsArticleRepository;
import com.newsaggregator.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    private final UserRepository userRepository;
    private final NewsArticleRepository newsArticleRepository;
    private final NewsAggregationService newsAggregationService;

    public AdminService(UserRepository userRepository, NewsArticleRepository newsArticleRepository, NewsAggregationService newsAggregationService) {
        this.userRepository = userRepository;
        this.newsArticleRepository = newsArticleRepository;
        this.newsAggregationService = newsAggregationService;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }

    @Transactional(readOnly = true)
    public List<NewsArticle> getAllArticles() {
        return newsArticleRepository.findAll();
    }

    @Transactional
    public void deleteArticle(Long id) {
        if (!newsArticleRepository.existsById(id)) {
            throw new RuntimeException("Article not found");
        }
        newsArticleRepository.deleteById(id);
        log.info("Deleted article with id: {}", id);
    }

    public void refreshNews() {
        log.info("Manual news refresh triggered by Admin");
        // Trigger async or synchronous depending on design. Since it might take time, we call it synchronously for now, or just let it run.
        newsAggregationService.aggregateNews();
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getSystemStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("apiStatus", "UP");
        status.put("dbStatus", "CONNECTED");
        status.put("totalUsers", userRepository.count());
        status.put("totalArticles", newsArticleRepository.count());
        return status;
    }
}
