package com.newsaggregator.presentation.controller;

import com.newsaggregator.application.service.NewsAggregationService;
import com.newsaggregator.domain.entity.NewsArticle;
import com.newsaggregator.infrastructure.repository.NewsArticleRepository;
import com.newsaggregator.infrastructure.repository.CategoryRepository;
import com.newsaggregator.infrastructure.repository.SourceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.newsaggregator.application.service.BookmarkService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Set;
import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsAggregationService newsAggregationService;
    private final NewsArticleRepository newsArticleRepository;
    private final BookmarkService bookmarkService;
    private final CategoryRepository categoryRepository;
    private final SourceRepository sourceRepository;

    public NewsController(NewsAggregationService newsAggregationService, 
                          NewsArticleRepository newsArticleRepository, 
                          BookmarkService bookmarkService,
                          CategoryRepository categoryRepository,
                          SourceRepository sourceRepository) {
        this.newsAggregationService = newsAggregationService;
        this.newsArticleRepository = newsArticleRepository;
        this.bookmarkService = bookmarkService;
        this.categoryRepository = categoryRepository;
        this.sourceRepository = sourceRepository;
    }

    @PostMapping("/fetch")
    public ResponseEntity<String> triggerFetch() {
        newsAggregationService.aggregateNews();
        return ResponseEntity.ok("News aggregation triggered successfully.");
    }

    @GetMapping
    public ResponseEntity<Page<NewsArticle>> getNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String source
    ) {
        Page<NewsArticle> articles = newsArticleRepository.findFilteredNews(
                query, category, source,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "publishedAt"))
        );
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            try {
                Set<NewsArticle> bookmarked = bookmarkService.getBookmarkedArticles(auth.getName());
                articles.forEach(article -> {
                    if (bookmarked.contains(article)) {
                        article.setBookmarked(true);
                    }
                });
            } catch (Exception e) {
                // Ignore if user not found (e.g. invalid token)
            }
        }
        
        return ResponseEntity.ok(articles);
    }

    @PostMapping("/{id}/bookmark")
    public ResponseEntity<String> toggleBookmark(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        bookmarkService.toggleBookmark(auth.getName(), id);
        return ResponseEntity.ok("Bookmark toggled successfully.");
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<Set<NewsArticle>> getBookmarks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(bookmarkService.getBookmarkedArticles(auth.getName()));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(categoryRepository.findAllNames());
    }

    @GetMapping("/sources")
    public ResponseEntity<List<String>> getSources() {
        return ResponseEntity.ok(sourceRepository.findAllNames());
    }
}
