package com.frontwit.barcode.reader.store;

import com.frontwit.barcode.reader.application.BarcodeStorage;
import com.frontwit.barcode.reader.application.ProcessBarcodeCommand;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class SQLiteBarcodeStorage implements BarcodeStorage {

    private SQLiteRepository sqLiteRepository;

    @Override
    public void store(ProcessBarcodeCommand processBarcodeCommand) {
        var readerId = processBarcodeCommand.getStage();
        var barcode = processBarcodeCommand.getBarcode();
        var dateTime = processBarcodeCommand.getDateTime();
        sqLiteRepository.persist(readerId, barcode, dateTime);
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
