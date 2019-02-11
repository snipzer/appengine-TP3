package com.zenika.zencontact.persistence.memcache;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class CacheService {
    private static MemcacheService INSTANCE = MemcacheServiceFactory.getMemcacheService();

    public static MemcacheService getINSTANCE() {
        return INSTANCE;
    }
}
