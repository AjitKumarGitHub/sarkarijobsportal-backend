package com.marbel.job.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(10000)                       // Max 10k items
                .expireAfterWrite(10, TimeUnit.MINUTES)    // Auto expiry
                .expireAfterAccess(5, TimeUnit.MINUTES)    // Remove unused items
                .refreshAfterWrite(5, TimeUnit.MINUTES)    // Background refresh
                .recordStats();                            // For performance metrics
    }

    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(
            Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(500)
        );
        return cacheManager;
    }

}


