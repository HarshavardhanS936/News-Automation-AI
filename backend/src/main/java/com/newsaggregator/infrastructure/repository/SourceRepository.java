package com.newsaggregator.infrastructure.repository;

import com.newsaggregator.domain.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {
    Optional<Source> findByName(String name);

    @Query("SELECT s.name FROM Source s ORDER BY s.name ASC")
    List<String> findAllNames();
}
