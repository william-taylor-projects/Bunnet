package com.ms.bunnet.controller;

import org.apache.commons.lang3.tuple.Pair;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/*
    Should provide information on the cache state
    for debugging reasons
 */
@RestController
public class CacheController {
    @Autowired()
    @Qualifier("DirectoryCache")
    private Cache<String, String> cache;

    @GetMapping("/files")
    public List<Pair<String, String>> getFilenames() {
        return StreamSupport.stream(cache.spliterator(), false)
                .map(i -> Pair.of(i.getKey(), i.getValue()))
                .collect(Collectors.toList());
    }
}
