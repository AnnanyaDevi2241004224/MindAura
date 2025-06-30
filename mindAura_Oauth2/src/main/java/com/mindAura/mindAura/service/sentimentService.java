package com.mindAura.mindAura.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class sentimentService {

    public String analyzeSentiment(String text) {
        String huggingFaceToken = "Bearer YOUR_HUGGINGFACE_TOKEN";
        String model = "distilbert-base-uncased-finetuned-sst-2-english";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", huggingFaceToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payload = Map.of("inputs", text);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> response = restTemplate.postForEntity(
                "https://api-inference.huggingface.co/models/" + model,
                request,
                List.class
        );

        List<Map<String, Object>> result = response.getBody();
        if (result != null && !result.isEmpty()) {
            return (String) result.get(0).get("label");
        }

        return "neutral";
    }
}
