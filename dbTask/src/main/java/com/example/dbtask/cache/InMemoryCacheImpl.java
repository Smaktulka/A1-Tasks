package com.example.dbtask.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryCacheImpl<T, U> implements InMemoryCache<T, U> {

    private Map<T, U> hashMap = new HashMap<>();

    @Override
    public U get(T request) {
        return hashMap.get(request);
    }

    @Override
    public void save(T request, U response) {
        if (!hashMap.containsKey(request)) {
            hashMap.put(request, response);
        }
    }
}
