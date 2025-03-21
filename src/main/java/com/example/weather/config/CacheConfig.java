package com.example.weather.config;

import com.example.weather.common.Constant;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
@EnableCaching
public class CacheConfig {
    private final CacheConfigProperties cacheConfigProperties;

    public CacheConfig(CacheConfigProperties cacheConfigProperties) {
        this.cacheConfigProperties = cacheConfigProperties;
    }

    @Bean(name = Constant.CACHE_MANAGER_DEFAULT)
    public CacheManager cacheManager() {
        switch(cacheConfigProperties.getManager()) {
            case "simpleCacheManager":
                SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
                simpleCacheManager.setCaches(cacheConfigProperties.getTtl().keySet()
                    .stream()
                    .map(ConcurrentMapCache::new)
                    .collect(Collectors.toList())
                );
                return simpleCacheManager;
            default:
                return new NoOpCacheManager();
        }
    }
}
