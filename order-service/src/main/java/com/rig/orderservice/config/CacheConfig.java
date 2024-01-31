package com.rig.orderservice.config;

import com.rig.orderservice.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Set;

@Configuration
@EnableCaching
@Slf4j
public class CacheConfig {

    private static final Set<ConcurrentMapCache> CACHES = Set.of(
            new ConcurrentMapCache(Cache.ORDER_CACHE),
            new ConcurrentMapCache(Cache.ORDER_LIST_CACHE)
    );

    @Bean
    public CacheManager smeFormsCacheManager() {
        final SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(CACHES);

        return cacheManager;
    }

    @Scheduled(fixedRateString = "${app.cache.order.ttl-ms}")
    @CacheEvict(Cache.ORDER_CACHE)
    public void evictOrderCache() {
        log.info("Refreshing order cache..");
    }

    @Scheduled(fixedRateString = "${app.cache.order-list.ttl-ms}")
    @CacheEvict(Cache.ORDER_LIST_CACHE)
    public void evictOrderListCache() {
        log.info("Refreshing order list cache..");
    }

}
