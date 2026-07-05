package com.newsaggregator.infrastructure.repository;

import com.newsaggregator.domain.entity.ArticleAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleAnalysisRepository extends JpaRepository<ArticleAnalysis, Long> {
    
    @Query("SELECT a.sentiment, COUNT(a) FROM ArticleAnalysis a GROUP BY a.sentiment")
    List<Object[]> countBySentiment();
    
    @Query("SELECT a.article.title, a.trendingScore FROM ArticleAnalysis a ORDER BY a.trendingScore DESC LIMIT 5")
    List<Object[]> findTopTrendingTopics();
    
    @Query("SELECT CASE " +
           "WHEN a.credibilityScore >= 85 THEN 'High' " +
           "WHEN a.credibilityScore >= 60 THEN 'Medium' " +
           "ELSE 'Low' END AS credibilityLevel, COUNT(a) " +
           "FROM ArticleAnalysis a GROUP BY CASE " +
           "WHEN a.credibilityScore >= 85 THEN 'High' " +
           "WHEN a.credibilityScore >= 60 THEN 'Medium' " +
           "ELSE 'Low' END")
    List<Object[]> countByCredibilityScoreBuckets();
}
