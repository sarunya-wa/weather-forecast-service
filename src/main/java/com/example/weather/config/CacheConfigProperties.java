package com.example.weather.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix="cache")
public class CacheConfigProperties {
    private String manager;
    private String prefix;
    private Map<String, Long> ttl;
}
