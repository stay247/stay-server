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
        // Set headers
        HttpHeaders headers = new HttpHeaders();

        // Set Entity (GET 요청의 경우 본문이 없으므로 null로 설정)
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        // Request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        return response.getBody();
    }

    public String post(String url, MultiValueMap<String, String> body) {
        // Set headers
        HttpHeaders headers = new HttpHeaders();

        // Set Entity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // Request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);


        return response.getBody();
    }
}
