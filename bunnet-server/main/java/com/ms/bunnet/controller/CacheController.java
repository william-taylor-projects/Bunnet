package com.ms.bunnet.controller;

import com.ms.bunnet.domain.FileData;
import com.ms.bunnet.domain.FileKey;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private Cache<FileKey, FileData> cache;

    @GetMapping("/files")
    public List<FileKey> getFiles() {
        return StreamSupport.stream(cache.spliterator(), true).map(i -> i.getKey()).collect(Collectors.toList());
    }

    @GetMapping("/files/{fileKey}")
    public FileData getFileData(@PathVariable(value="fileKey") String fileKey) {
        return cache.get(new FileKey(fileKey));
    }
}
