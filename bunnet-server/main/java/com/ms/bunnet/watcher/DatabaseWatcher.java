package com.ms.bunnet.watcher;

import org.ehcache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseWatcher implements Runnable {
    private Logger logger = LoggerFactory.getLogger(DatabaseWatcher.class);

    @Autowired()
    @Qualifier("RocksDBCache")
    private Cache<String, String> cache;

    @Value("${watcher.database.path}")
    private String databasePath;

    @Value("${watcher.database.name}")
    private String databaseName;

    @Override
    public void run() {
        logger.info("Hi from RocksDB Watcher!");
        /*
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            Path path = Paths.get("/var/tmp");
            path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println(
                            "Event kind:" + event.kind()
                                    + ". File affected: " + event.context() + ".");
                }
                key.reset();
            }
        } catch(InterruptedException e) {
            logger.error(e.getMessage(), e);
        } catch(IOException e) {
            logger.error(e.getMessage(), e);
        }*/

        /*
        List<String> columnFamilies = RocksDB.listColumnFamilies(new Options(), "/var/tmp/tmpdb")
                .stream()
                .map(String::new)
                .collect(Collectors.toList());

        logger.info(columnFamilies.toString());



        try (final ColumnFamilyOptions cfOpts = new ColumnFamilyOptions().optimizeUniversalStyleCompaction()) {
            // list of column family descriptors, first entry must always be default column family
            final List<ColumnFamilyDescriptor> cfDescriptors = Arrays.asList(
                new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, cfOpts),
                new ColumnFamilyDescriptor("fxrates".getBytes(), cfOpts)
            );

            // a list which will hold the handles for the column families once the db is opened
            final List<ColumnFamilyHandle> columnFamilyHandleList = new ArrayList<>();
            final DBOptions options = new DBOptions();
            options.setCreateMissingColumnFamilies(true);
            options.setCreateIfMissing(true);
            options.setErrorIfExists(false);

            try (final RocksDB db = RocksDB.openReadOnly(options, "/var/tmp/tmpdb", cfDescriptors, columnFamilyHandleList)) {
                try {
                    FxRate inRate = new FxRate("British Pound", "GBP", BigDecimal.ONE, 'M');
                    db.put(columnFamilyHandleList.get(1), "key".getBytes(), SerializationUtils.serialize(inRate));
                    FxRate outRate = (FxRate)SerializationUtils.deserialize(db.get(columnFamilyHandleList.get(1), "key".getBytes()));
                    logger.info(outRate.toString());
                } finally {
                    // NOTE frees the column family handles before freeing the db
                    for (final ColumnFamilyHandle columnFamilyHandle : columnFamilyHandleList) {
                        columnFamilyHandle.close();
                    }
                } // frees the db and the db options
            }
        } // frees the column family options
        */
    }
}
