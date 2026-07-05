package com.newsaggregator.application.service.analysis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiAiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public GeminiAiService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("https://generativelanguage.googleapis.com").build();
        this.objectMapper = objectMapper;
    }

    public AiAnalysisResult analyzeArticle(String title, String content) {
        if ("default_key".equals(apiKey) || apiKey == null || apiKey.isEmpty()) {
            System.err.println("Gemini API key is not configured. Falling back to mocks.");
            return fallbackMockResult(title, content);
        }

        try {
            String prompt = String.format(
                "You are an expert news analyst AI. Analyze the following news article.\\n" +
                "Title: %s\\nContent: %s\\n\\n" +
                "Respond ONLY with a valid JSON object in this exact format:\\n" +
                "{\\n" +
                "  \\\"sentiment\\\": \\\"POSITIVE\\\" or \\\"NEGATIVE\\\" or \\\"NEUTRAL\\\",\\n" +
                "  \\\"credibilityScore\\\": a number between 0 and 100,\\n" +
                "  \\\"category\\\": a single word category (e.g., Technology, Finance, Politics, Science, World, Health, Sports, General)\\n" +
                "}", 
                title, 
                content != null && content.length() > 2000 ? content.substring(0, 2000) : content // limit length
            );

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", List.of(
                Map.of("parts", List.of(
                    Map.of("text", prompt)
                ))
            ));

            String responseString = webClient.post()
                    .uri("/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode rootNode = objectMapper.readTree(responseString);
            String aiTextResponse = rootNode
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            // Clean markdown JSON formatting if present
            aiTextResponse = aiTextResponse.replace("```json", "").replace("```", "").trim();
            
            return objectMapper.readValue(aiTextResponse, AiAnalysisResult.class);

        } catch (Exception e) {
            System.err.println("Error calling Gemini API: " + e.getMessage());
            e.printStackTrace();
            return fallbackMockResult(title, content);
        }
    }

    private AiAnalysisResult fallbackMockResult(String title, String content) {
        AiAnalysisResult mock = new AiAnalysisResult();
        String lowerContent = (title + " " + content).toLowerCase();
        
        if (lowerContent.contains("good") || lowerContent.contains("success")) mock.setSentiment("POSITIVE");
        else if (lowerContent.contains("bad") || lowerContent.contains("fail")) mock.setSentiment("NEGATIVE");
        else mock.setSentiment("NEUTRAL");

        mock.setCredibilityScore(75.0);

        if (lowerContent.contains("tech") || lowerContent.contains("software")) mock.setCategory("Technology");
        else if (lowerContent.contains("finance") || lowerContent.contains("market")) mock.setCategory("Finance");
        else mock.setCategory("General");

        return mock;
    }

    public static class AiAnalysisResult {
        private String sentiment;
        private Double credibilityScore;
        private String category;

        public String getSentiment() { return sentiment; }
        public void setSentiment(String sentiment) { this.sentiment = sentiment; }
        public Double getCredibilityScore() { return credibilityScore; }
        public void setCredibilityScore(Double credibilityScore) { this.credibilityScore = credibilityScore; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }
}
