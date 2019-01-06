package com.ms.bunnet.controller;

import com.ms.bunnet.domain.FileData;
import com.ms.bunnet.domain.FileKey;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class BasicSocketController {
    @Autowired()
    @Qualifier("DirectoryCache")
    private Cache<FileKey, FileData> cache;

    @MessageMapping("/filenames")
    @SendTo("/response/filenames")
    public List<FileKey> test() {
        return StreamSupport.stream(cache.spliterator(), true).map(i -> i.getKey()).collect(Collectors.toList());
    }
}
