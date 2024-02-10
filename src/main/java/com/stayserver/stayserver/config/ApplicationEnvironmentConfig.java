package com.stayserver.stayserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class ApplicationEnvironmentConfig {

    private final Environment environment;

    public String getConfigValue(String key) {
        return environment.getProperty(key);
    }
}