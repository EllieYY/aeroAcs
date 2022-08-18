package com.wim.aero.acs.util.cache;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @title: ExpireCacheManager
 * @author: Ellie
 * @date: 2022/06/08 10:45
 * @description:
 **/
@Data
@Component
@Slf4j
public class ExpireCacheManager extends ConcurrentHashMap<String, Cache<?, ?>> implements CacheManager {

    @Override
    public void startManager() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (String key : ExpireCacheManager.super.keySet()) {
                    ExpireCache cache = (ExpireCache) ExpireCacheManager.super.get(key);
                    for (Object expiredCacheKey : cache.keySet()) {
                        Object obj = cache.get(expiredCacheKey);
                        /*
                         * 超时了移除！
                         * 另外：有 load 的总是不会返回 null，所以这里不用考虑有 load 的 ExpireCacheData。
                         * 而且有 load 的也不应该被 kill
                         */
                        if (obj == null && !key.equals("seqCache")) {
//                            log.info("[timeout remove] {} : {}", key, expiredCacheKey);
                            cache.remove(expiredCacheKey);
                        }
                    }
                    log.info("{} : {}", key, cache.size());
                }
            }
        }, 0, 10 * 1000); // 10 秒钟清理一次
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        @SuppressWarnings("unchecked")
        Cache<K, V> cache = (Cache<K, V >) get(name);

        if (cache == null) {
            cache = new ExpireCache<K, V>();
            put(name, cache);
        }

        return cache;
    }



    @Override
    public <V> Cache<String, V> getCache(String name, Class<V> clz) {
        @SuppressWarnings("unchecked")
        Cache<String, V> cache = (Cache<String, V>) get(name);
        if (cache == null) {
            cache = new ExpireCache<String, V>();
            put(name, cache);
        }

        return cache;
    }

    @Override
    public void destroy() {
        super.clear();
    }
}
