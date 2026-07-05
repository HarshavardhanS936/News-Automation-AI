package com.newsaggregator.infrastructure.repository;

import com.newsaggregator.domain.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    boolean existsByUrl(String url);
    Optional<NewsArticle> findByUrl(String url);

    @Query("SELECT DISTINCT n FROM NewsArticle n LEFT JOIN n.categories c WHERE " +
           "(:query IS NULL OR LOWER(n.title) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:category IS NULL OR c.name = :category) AND " +
           "(:source IS NULL OR n.source.name = :source)")
    org.springframework.data.domain.Page<NewsArticle> findFilteredNews(
            @org.springframework.data.repository.query.Param("query") String query, 
            @org.springframework.data.repository.query.Param("category") String category, 
            @org.springframework.data.repository.query.Param("source") String source, 
            org.springframework.data.domain.Pageable pageable);

    @Query("SELECT s.name, COUNT(a) FROM NewsArticle a JOIN a.source s GROUP BY s.name")
    List<Object[]> countBySource();

    @Query("SELECT c.name, COUNT(a) FROM NewsArticle a JOIN a.categories c GROUP BY c.name")
    List<Object[]> countByCategory();

    @Query("SELECT CAST(a.publishedAt as date), COUNT(a) FROM NewsArticle a GROUP BY CAST(a.publishedAt as date) ORDER BY CAST(a.publishedAt as date) ASC")
    List<Object[]> countByPublishedDate();
}
