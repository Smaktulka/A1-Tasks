package com.example.dbtask.cache;

public interface InMemoryCache <T, U>{

    U get(T request);
    void save(T request, U response);
}
