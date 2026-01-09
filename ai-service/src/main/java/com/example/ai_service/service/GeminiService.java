package com.example.ai_service.service;

import com.example.ai_service.dto.GeminiRequest;
import com.example.ai_service.dto.GeminiResponse;
import com.example.ai_service.exception.GeminiServiceException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

@Service
@Slf4j
public class GeminiService {
    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;
    
    private static final String API_ENDPOINT = "/v1beta/models/gemini-2.5-flash:generateContent";

    // Constructor injection of WebClient.Builder
    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getAnswer(String question) {
        try {
            // Create request using the proper DTO structure
            GeminiRequest request = createRequest(question);

            String fullUrl = geminiApiUrl + API_ENDPOINT + "?key=" + geminiApiKey;
            log.debug("Calling Gemini API at: {}", geminiApiUrl + API_ENDPOINT); // Don't log the key

            // Make API call using the instance webClient
            GeminiResponse response = webClient.post()
                    .uri(fullUrl)
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(GeminiResponse.class)
                    .block();

            // Extract text from response
            if (response == null || response.candidates() == null || response.candidates().isEmpty()) {
                throw new GeminiServiceException("Empty response from Gemini API");
            }

            return extractTextFromResponse(response);
        } catch (WebClientResponseException e) {
            log.error("Gemini API Error - Status: {}, Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new GeminiServiceException("Error calling Gemini API: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error calling Gemini API", e);
            throw new GeminiServiceException("Unexpected error: " + e.getMessage(), e);
        }
    }

    private GeminiRequest createRequest(String question) {
        // Create the nested objects using the correct syntax for nested records
        GeminiRequest.Content.Part part = new GeminiRequest.Content.Part(question);
        List<GeminiRequest.Content.Part> parts = Collections.singletonList(part);

        GeminiRequest.Content content = new GeminiRequest.Content(parts);
        List<GeminiRequest.Content> contents = Collections.singletonList(content);

        return new GeminiRequest(contents);
    }

    private String extractTextFromResponse(GeminiResponse response) {
        return response.candidates().stream()
                .findFirst()
                .map(GeminiResponse.Candidate::content)
                .map(GeminiResponse.Content::parts)
                .orElse(Collections.emptyList())
                .stream()
                .findFirst()
                .map(GeminiResponse.Part::text)
                .orElseThrow(() -> new GeminiServiceException("No text found in response"));
    }
}
