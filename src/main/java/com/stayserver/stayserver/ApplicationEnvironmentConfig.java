package com.stayserver.stayserver;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEnvironmentConfig {

    private final Environment environment;

    public ApplicationEnvironmentConfig(Environment environment) {
        this.environment = environment;
    }

    public String getConfigValue(String key) {
        return environment.getProperty(key);
    }
}