package com.snipzer.contact.util;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class CacheUtil {
    private static MemcacheService INSTANCE = MemcacheServiceFactory.getMemcacheService();

    public static MemcacheService getInstance() {
        return INSTANCE;
    }
}
