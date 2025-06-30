package com.MindAura.MindAura.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SentimentService {

    @Value("${huggingface.api.token}")
    private String apiToken;

    private final RestTemplate restTemplate = new RestTemplate();

    public String analyzeSentiment(String content) {
        String url = "https://api-inference.huggingface.co/models/cardiffnlp/twitter-roberta-base-sentiment";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiToken);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("inputs", content), headers);

        try {
            ResponseEntity<List> response = restTemplate.postForEntity(url, request, List.class);
            List<Map<String, Object>> predictions = (List<Map<String, Object>>) response.getBody().get(0);

            return predictions.stream()
                    .max(Comparator.comparingDouble(p -> (Double) p.get("score")))
                    .map(p -> (String) p.get("label"))
                    .orElse("Neutral");
        } catch (Exception e) {
            return "Unknown";
        }
    }
}
