package com.stayserver.stayserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationEnvironmentConfig {

    private final Environment environment;

    public String getConfigValue(String key) {
        return environment.getProperty(key);
    }
}