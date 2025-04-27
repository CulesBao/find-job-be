package com.findjobbe.findjobbe.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.findjobbe.findjobbe.service.IChatService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiServiceImpl implements IChatService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${gemini-api-key}")
    private String apiKey;

    public String reply(String message) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey;

        Map<String, Object> body = new HashMap<>();
        body.put("contents", new Object[]{
                Map.of("parts", new Object[]{
                        Map.of("text", message)
                })
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
      ResponseEntity<String> response =
          restTemplate.postForEntity(url, requestEntity, String.class);

            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode candidates = root.path("candidates");
            if (candidates.isArray() && candidates.size() > 0) {
                return candidates.get(0).path("content").path("parts").get(0).path("text").asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Sorry, I couldn't process your request.";
    }
}
