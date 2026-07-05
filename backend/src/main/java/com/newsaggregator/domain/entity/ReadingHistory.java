package com.newsaggregator.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reading_history", indexes = {
    @Index(name = "idx_reading_history_user", columnList = "user_id")
})
public class ReadingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private NewsArticle article;

    @CreationTimestamp
    @Column(name = "read_at", updatable = false)
    private LocalDateTime readAt;

    public ReadingHistory() {}
    public ReadingHistory(Long id, User user, NewsArticle article, LocalDateTime readAt) {
        this.id = id; this.user = user; this.article = article; this.readAt = readAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public NewsArticle getArticle() { return article; }
    public void setArticle(NewsArticle article) { this.article = article; }
    public LocalDateTime getReadAt() { return readAt; }
    public void setReadAt(LocalDateTime readAt) { this.readAt = readAt; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id;
        private User user;
        private NewsArticle article;
        private LocalDateTime readAt;
        public Builder id(Long id) { this.id = id; return this; }
        public Builder user(User user) { this.user = user; return this; }
        public Builder article(NewsArticle article) { this.article = article; return this; }
        public Builder readAt(LocalDateTime readAt) { this.readAt = readAt; return this; }
        public ReadingHistory build() { return new ReadingHistory(id, user, article, readAt); }
    }
}
