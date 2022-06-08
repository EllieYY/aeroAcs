package com.wim.aero.acs;

import com.wim.aero.acs.util.cache.ExpireCache;
import com.wim.aero.acs.util.cache.ExpireCacheManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @title: TestCache
 * @author: Ellie
 * @date: 2022/06/08 09:53
 * @description:
 **/
public class TestCache {
    @Autowired
    private ExpireCacheManager expireCacheManager;


    @Test
    public void testExpire() throws InterruptedException {
        expireCacheManager.getCache("test1");

//        ExpireCache.CACHE.put("foo", "bar", 3);
//
//        Thread.sleep(1000);
//        assertEquals("bar", ExpireCache.CACHE.get("foo"));
//
//        Thread.sleep(2010);
//        assertNull(ExpireCache.CACHE.get("foo"));
//
//        ExpireCache.CACHE.put("bar", "foo", 2, () -> "foo-2");
//
//        Thread.sleep(1000);
//        assertEquals("foo", ExpireCache.CACHE.get("bar"));
//
//        Thread.sleep(2010);
//        assertEquals("foo-2", ExpireCache.CACHE.get("bar"));
//        assertNull(ExpireCache.CACHE.get("foo"));
    }
}
