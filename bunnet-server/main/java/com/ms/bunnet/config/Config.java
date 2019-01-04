package com.ms.bunnet.config;

import com.ms.bunnet.domain.FileData;
import com.ms.bunnet.domain.FileKey;
import com.ms.bunnet.watcher.DirectoryWatcher;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.List;

@Configuration
public class Config {
    @Bean
    public CacheManager cacheManager() {
        CacheManager manager = CacheManagerBuilder.newCacheManagerBuilder().build();
        manager.init();
        return manager;
    }

    @Bean(name="DirectoryCache")
    public Cache<FileKey, FileData> directoryCache(@Autowired CacheManager manager) {
        return manager.createCache("DirectoryCache", CacheConfigurationBuilder
                .newCacheConfigurationBuilder(FileKey.class, FileData.class, ResourcePoolsBuilder.heap(10)));
    }

    @Bean
    public DirectoryWatcher directoryWatcher() {
        return new DirectoryWatcher();
    }

    @Bean
    public List<Runnable> backgroundRunnables() {
        return Arrays.asList(directoryWatcher());
    }

    @Bean
    public CommandLineRunner backgroundTasks(TaskExecutor executor) {
        return args -> backgroundRunnables().forEach(w -> executor.execute(w));
    }

    @Bean
    @ConfigurationProperties("spring.task.executor")
    public TaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean
    @Scope("prototype")
    public WatchService watchService() throws IOException {
        return FileSystems.getDefault().newWatchService();
    }
}
