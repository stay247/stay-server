package com.stayserver.stayserver.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestUtil {

    private final RestTemplate restTemplate;

    public String get(String url) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        return response.getBody();
    }

    public String post(String url, MultiValueMap<String, String> body) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        return response.getBody();
    }

    // 토큰
    public String get(String url, String accessToken, String tokenType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenType + " " + accessToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return response.getBody();
    }

    // 토큰
    public String post(String url, MultiValueMap<String, String> body, String accessToken, String tokenType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenType + " " + accessToken);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        return response.getBody();
    }
}
