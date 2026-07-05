package com.newsaggregator.application.service.analysis;

import com.newsaggregator.domain.entity.Category;
import com.newsaggregator.domain.entity.NewsArticle;
import com.newsaggregator.infrastructure.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CategorizationService {

    private final CategoryRepository categoryRepository;

    // Simple keyword mapping for auto-categorization
    private final Map<String, List<String>> categoryKeywords = Map.of(
            "Technology", Arrays.asList("tech", "software", "apple", "google", "microsoft", "ai", "artificial intelligence", "cyber", "digital", "startup", "app"),
            "Finance", Arrays.asList("finance", "stock", "market", "economy", "bank", "invest", "money", "crypto", "bitcoin", "inflation"),
            "Politics", Arrays.asList("politics", "election", "president", "government", "policy", "congress", "senate", "democrat", "republican", "law"),
            "Health", Arrays.asList("health", "disease", "covid", "virus", "hospital", "doctor", "medical", "vaccine", "cancer"),
            "Science", Arrays.asList("science", "space", "nasa", "research", "physics", "biology", "climate", "environment", "mars"),
            "World", Arrays.asList("world", "global", "international", "un", "united nations", "europe", "asia", "africa", "war", "peace"),
            "Sports", Arrays.asList("sports", "football", "basketball", "soccer", "nfl", "nba", "athlete", "olympics", "championship")
    );

    public CategorizationService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void categorize(NewsArticle article) {
        String contentToAnalyze = (article.getTitle() + " " + article.getContent()).toLowerCase();
        Set<Category> assignedCategories = new HashSet<>();

        for (Map.Entry<String, List<String>> entry : categoryKeywords.entrySet()) {
            for (String keyword : entry.getValue()) {
                if (contentToAnalyze.contains(keyword)) {
                    Category category = categoryRepository.findByName(entry.getKey())
                            .orElseGet(() -> categoryRepository.save(Category.builder().name(entry.getKey()).build()));
                    assignedCategories.add(category);
                    break; // Move to next category once one keyword matches for this category
                }
            }
        }

        // Default category if nothing matched
        if (assignedCategories.isEmpty()) {
            Category general = categoryRepository.findByName("General")
                    .orElseGet(() -> categoryRepository.save(Category.builder().name("General").build()));
            assignedCategories.add(general);
        }

        article.setCategories(assignedCategories);
    }

    public void categorizeWithAi(NewsArticle article, String aiCategoryStr) {
        if (aiCategoryStr == null || aiCategoryStr.trim().isEmpty()) {
            categorize(article); // fallback
            return;
        }

        String rawCategory = aiCategoryStr.trim();
        // Capitalize first letter for consistency
        final String safeCategory = rawCategory.substring(0, 1).toUpperCase() + rawCategory.substring(1).toLowerCase();

        Category category = categoryRepository.findByName(safeCategory)
                .orElseGet(() -> categoryRepository.save(Category.builder().name(safeCategory).build()));
        
        Set<Category> assignedCategories = new HashSet<>();
        assignedCategories.add(category);
        article.setCategories(assignedCategories);
    }
}
