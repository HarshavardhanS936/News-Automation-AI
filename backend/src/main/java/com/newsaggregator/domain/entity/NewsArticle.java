package com.newsaggregator.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "news_articles", indexes = {
    @Index(name = "idx_news_article_published_at", columnList = "published_at"),
    @Index(name = "idx_news_article_url", columnList = "url")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column(nullable = false, unique = true, length = 768)
    private String url;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @ManyToMany
    @JoinTable(
            name = "article_categories",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @Transient
    private boolean isBookmarked = false;

    public NewsArticle() {}
    public NewsArticle(Long id, String title, String content, String url, String imageUrl, LocalDateTime publishedAt, Source source, Set<Category> categories) {
        this.id = id; this.title = title; this.content = content; this.url = url; this.imageUrl = imageUrl; this.publishedAt = publishedAt; this.source = source; this.categories = categories;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
    public Source getSource() { return source; }
    public void setSource(Source source) { this.source = source; }
    public Set<Category> getCategories() { return categories; }
    public void setCategories(Set<Category> categories) { this.categories = categories; }
    public boolean isBookmarked() { return isBookmarked; }
    public void setBookmarked(boolean isBookmarked) { this.isBookmarked = isBookmarked; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id;
        private String title;
        private String content;
        private String url;
        private String imageUrl;
        private LocalDateTime publishedAt;
        private Source source;
        private Set<Category> categories = new HashSet<>();
        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder content(String content) { this.content = content; return this; }
        public Builder url(String url) { this.url = url; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder publishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; return this; }
        public Builder source(Source source) { this.source = source; return this; }
        public Builder categories(Set<Category> categories) { this.categories = categories; return this; }
        public NewsArticle build() { return new NewsArticle(id, title, content, url, imageUrl, publishedAt, source, categories); }
    }
}
