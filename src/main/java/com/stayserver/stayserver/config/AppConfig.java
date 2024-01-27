package com.stayserver.stayserver.config;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        // 필요한 경우 RestTemplate의 설정을 커스터마이징할 수 있습니다.
        return new RestTemplate();
    }
}
