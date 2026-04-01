package com.adamidis.learning.warehousestockflow.Config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("lowStockProducts", "reports", "outOfStockProducts");
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(10_000) // the maximum number of entries the cache can hold before evicting old entries. In this case, it can hold up to 10,000 entries.
                .expireAfterWrite(Duration.ofMinutes(5))); // the duration after which an entry will be automatically removed from the cache if it has not been accessed or updated. In this case, entries will expire 5 minutes after they are written to the cache.

        return caffeineCacheManager;
    }
}
