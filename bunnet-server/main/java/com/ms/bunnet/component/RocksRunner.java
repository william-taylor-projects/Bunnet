package com.ms.bunnet.component;

import com.ms.bunnet.domain.FxRate;
import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RocksRunner implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(RocksRunner.class);

    @Override
    public void run(String... args) throws Exception {

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

            try (final RocksDB db = RocksDB.open(options, "/var/tmp/tmpdb", cfDescriptors, columnFamilyHandleList)) {
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
    }
}
