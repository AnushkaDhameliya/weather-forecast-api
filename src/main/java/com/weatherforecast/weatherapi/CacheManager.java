package com.weatherforecast.weatherapi;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;

@Component
@Slf4j
public class CacheManager {

    private final HashMap<HashSet<String>, Object> cache = new HashMap<>();

    public Object get(HashSet<String> query){
        if(cache.containsKey(query)){
            return cache.get(query);
        }
        return null;
    }

    public void put(HashSet<String> query, Object response) {
        cache.put(query,response);
    }

}
