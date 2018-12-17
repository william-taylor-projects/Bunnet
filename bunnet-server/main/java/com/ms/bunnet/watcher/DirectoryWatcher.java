package com.ms.bunnet.watcher;

import org.ehcache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

@Component
public class DirectoryWatcher implements Runnable {
    private Logger logger = LoggerFactory.getLogger(DirectoryWatcher.class);

    @Autowired()
    @Qualifier("DirectoryCache")
    private Cache<String, String> cache;

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
                    .map(p -> p.getFileName().toString())
                    .forEach(this::insertFile);
        } catch(IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void watchForChanges() {
        try {
            WatchKey key;
            while ((key = watchService.take()) != null) {
                // TODO: Deal with file events rather than just adding the file
                key.pollEvents().parallelStream()
                        .map(e -> e.context().toString())
                        .filter(f -> !f.endsWith(".swp"))
                        .distinct()
                        .forEach(this::insertFile);
                key.reset();
            }
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void insertFile(String filename) {
        logger.info("Received events for this file " + filename + " loading into the cache");
        try {
            cache.put(filename, new String(Files.readAllBytes(Paths.get(watcherPath, filename))));
        } catch(IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
