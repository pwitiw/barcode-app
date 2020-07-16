package com.frontwit.barcode.reader.store;

import com.frontwit.barcode.reader.application.BarcodeStorage;
import com.frontwit.barcode.reader.application.ProcessFrontCommand;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class SQLiteBarcodeStorage implements BarcodeStorage {
    private static final Logger LOG = LoggerFactory.getLogger(SQLiteBarcodeStorage.class.getName());

    private SQLiteRepository sqLiteRepository;

    @Override
    public void store(ProcessFrontCommand processFrontCommand) {
        var readerId = processFrontCommand.getStage();
        var barcode = processFrontCommand.getBarcode();
        var dateTime = processFrontCommand.getDateTime();
        sqLiteRepository.persist(readerId, barcode, dateTime);
        LOG.info("Stored: {}", processFrontCommand);
    }

    @Override
    public Map<UUID, ProcessFrontCommand> findAll() {
        return sqLiteRepository.findAll();
    }

    @Override
    public void delete(Collection<UUID> ids) {
        sqLiteRepository.delete(ids);
    }
}
