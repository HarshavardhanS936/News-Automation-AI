package com.newsaggregator.presentation.controller;

import com.newsaggregator.application.service.AdminService;
import com.newsaggregator.domain.entity.NewsArticle;
import com.newsaggregator.domain.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/articles")
    public ResponseEntity<List<NewsArticle>> getAllArticles() {
        return ResponseEntity.ok(adminService.getAllArticles());
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        adminService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/articles/refresh")
    public ResponseEntity<Map<String, String>> refreshNews() {
        adminService.refreshNews();
        return ResponseEntity.ok(Map.of("message", "News refresh initiated successfully."));
    }

    @GetMapping("/system/status")
    public ResponseEntity<Map<String, Object>> getSystemStatus() {
        return ResponseEntity.ok(adminService.getSystemStatus());
    }
}
