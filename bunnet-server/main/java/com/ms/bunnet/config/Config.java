package com.ms.bunnet.config;

import com.ms.bunnet.watcher.DirectoryWatcher;
import com.ms.bunnet.watcher.DatabaseWatcher;
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
    public Cache<String, String> directoryCache(@Autowired CacheManager manager) {
        return manager.createCache("DirectoryCache", CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10)));
    }

    @Bean(name="RocksDBCache")
    public Cache<String, String> rocksDBCache(@Autowired CacheManager manager) {
        return manager.createCache("RocksDBCache", CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10)));
    }

    @Bean
    public DirectoryWatcher directoryWatcher() {
        return new DirectoryWatcher();
    }

    @Bean()
    public DatabaseWatcher rocksDBWatcher() {
        return new DatabaseWatcher();
    }

    @Bean
    public List<Runnable> cacheWatchers() {
        return Arrays.asList(directoryWatcher(), rocksDBWatcher());
    }

    @Bean
    public CommandLineRunner schedulingRunner(TaskExecutor executor) {
        return args -> cacheWatchers().forEach(w -> executor.execute(w));
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
