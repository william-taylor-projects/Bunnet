package com.ms.bunnet.watcher;

import com.ms.bunnet.domain.FileData;
import com.ms.bunnet.domain.FileKey;
import org.apache.commons.lang3.tuple.Pair;
import org.ehcache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.StandardWatchEventKinds.*;

@Component
public class DirectoryWatcher implements Runnable {
    private Logger logger = LoggerFactory.getLogger(DirectoryWatcher.class);

    @Autowired()
    @Qualifier("DirectoryCache")
    private Cache<FileKey, FileData> cache;

    @Autowired
    private WatchService watchService;

    @Value("${watcher.directory.path}")
    private String watcherPath;

    @Value("${watcher.directory.exclude}")
    private String watchExclusions;

    @Override
    public void run() {
        registerWatchService();
        populateCache();
        watchForChanges();
    }

    private void registerWatchService() {
        try {
            Path path = Paths.get(watcherPath);
            path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        } catch(IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void populateCache() {
        try {
            Files.list(Paths.get(watcherPath))
                    .map(p -> Pair.of(StandardWatchEventKinds.ENTRY_CREATE, p.getFileName().toString()))
                    .forEach(this::handleFileEvent);
        } catch(IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void watchForChanges() {
        try {
            WatchKey key;
            while ((key = watchService.take()) != null) {
                key.pollEvents().parallelStream()
                        .filter(e -> !e.kind().equals(StandardWatchEventKinds.OVERFLOW))
                        .map(e -> Pair.of((WatchEvent.Kind<Path>)e.kind(), e.context().toString()))
                        .filter(f -> !f.getRight().endsWith(".swp"))
                        .distinct()
                        .forEach(this::handleFileEvent);
                key.reset();
            }
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void handleFileEvent(Pair<WatchEvent.Kind<Path>, String> eventPair) {
        WatchEvent.Kind<Path> event = eventPair.getLeft();
        String filename = eventPair.getRight();
        FileKey key = new FileKey(filename);

        if(event.equals(StandardWatchEventKinds.ENTRY_DELETE) && cache.containsKey(key)) {
            logger.info("File deleted " + filename + " removing from cache!");
            // No need for removal all, filenames will be unique
            cache.remove(key);
        } else {
            try {
                Path fullFilePath = Paths.get(watcherPath, filename);
                logger.info("File created or update " + key.getName() + " adding to the cache!");
                BasicFileAttributes attributes = Files.readAttributes(fullFilePath, BasicFileAttributes.class);
                cache.put(new FileKey(filename, attributes), new FileData(Files.readAllBytes(fullFilePath)));
            } catch(IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
