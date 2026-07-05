package com.newsaggregator.application.service;

import com.newsaggregator.domain.entity.NewsArticle;
import com.newsaggregator.domain.entity.User;
import com.newsaggregator.infrastructure.repository.NewsArticleRepository;
import com.newsaggregator.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class BookmarkService {

    private final UserRepository userRepository;
    private final NewsArticleRepository newsArticleRepository;

    public BookmarkService(UserRepository userRepository, NewsArticleRepository newsArticleRepository) {
        this.userRepository = userRepository;
        this.newsArticleRepository = newsArticleRepository;
    }

    @Transactional
    public void toggleBookmark(String username, Long articleId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        NewsArticle article = newsArticleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        if (user.getBookmarkedArticles().contains(article)) {
            user.getBookmarkedArticles().remove(article);
        } else {
            user.getBookmarkedArticles().add(article);
        }
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Set<NewsArticle> getBookmarkedArticles(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Set<NewsArticle> articles = user.getBookmarkedArticles();
        articles.forEach(a -> a.setBookmarked(true));
        return articles;
    }
}
