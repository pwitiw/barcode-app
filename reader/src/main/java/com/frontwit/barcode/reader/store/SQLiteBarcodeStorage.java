package com.frontwit.barcode.reader.store;

import com.frontwit.barcode.reader.application.BarcodeStorage;
import com.frontwit.barcode.reader.application.ProcessBarcodeCommand;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;

@AllArgsConstructor
public class SQLiteBarcodeStorage implements BarcodeStorage {
    private static final Logger LOG = LoggerFactory.getLogger(SQLiteBarcodeStorage.class.getName());

    private SQLiteRepository sqLiteRepository;

    @Override
    public void store(ProcessBarcodeCommand processBarcodeCommand) {
        var readerId = processBarcodeCommand.getStage();
        var barcode = processBarcodeCommand.getBarcode();
        var dateTime = processBarcodeCommand.getDateTime();
        sqLiteRepository.persist(readerId, barcode, dateTime);
        LOG.info(format("[SQLite] Stored barcode %s", processBarcodeCommand));
    }

    @Override
    public Map<UUID, ProcessBarcodeCommand> findAll() {
        return sqLiteRepository.findAll();
    }

    @Override
    public void delete(Collection<UUID> ids) {
        sqLiteRepository.delete(ids);
    }
}
