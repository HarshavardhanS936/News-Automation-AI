package com.newsaggregator.domain.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_preferences")
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "user_preferred_categories",
            joinColumns = @JoinColumn(name = "preference_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> preferredCategories = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_preferred_sources",
            joinColumns = @JoinColumn(name = "preference_id"),
            inverseJoinColumns = @JoinColumn(name = "source_id")
    )
    private Set<Source> preferredSources = new HashSet<>();

    public UserPreference() {}
    public UserPreference(Long id, User user, Set<Category> preferredCategories, Set<Source> preferredSources) {
        this.id = id; this.user = user; this.preferredCategories = preferredCategories; this.preferredSources = preferredSources;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Set<Category> getPreferredCategories() { return preferredCategories; }
    public void setPreferredCategories(Set<Category> preferredCategories) { this.preferredCategories = preferredCategories; }
    public Set<Source> getPreferredSources() { return preferredSources; }
    public void setPreferredSources(Set<Source> preferredSources) { this.preferredSources = preferredSources; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id;
        private User user;
        private Set<Category> preferredCategories = new HashSet<>();
        private Set<Source> preferredSources = new HashSet<>();
        public Builder id(Long id) { this.id = id; return this; }
        public Builder user(User user) { this.user = user; return this; }
        public Builder preferredCategories(Set<Category> preferredCategories) { this.preferredCategories = preferredCategories; return this; }
        public Builder preferredSources(Set<Source> preferredSources) { this.preferredSources = preferredSources; return this; }
        public UserPreference build() { return new UserPreference(id, user, preferredCategories, preferredSources); }
    }
}
