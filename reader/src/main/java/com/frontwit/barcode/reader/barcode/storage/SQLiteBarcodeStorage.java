package com.frontwit.barcode.reader.barcode.storage;

import com.frontwit.barcode.reader.barcode.BarcodeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SQLiteBarcodeStorage implements BarcodeStorage {

    private SQLiteManager sqLiteManager;

    @Autowired
    public SQLiteBarcodeStorage(SQLiteManager sqLiteManager) {
        this.sqLiteManager = sqLiteManager;
    }

    @Override
    public void save(BarcodeCommand barcodeCommand) {
        Integer readerId = barcodeCommand.getReaderId();
        Long barcode = barcodeCommand.getBarcode();
        sqLiteManager.persist(readerId, barcode);
    }

    @Override
    public Collection<BarcodeCommand> findAll() {
        return sqLiteManager.findAll();
    }

    @Override
    public void delete(Collection<BarcodeCommand> barcodes) {
        // TOdO czy command opakowywac  w entity
        Collection<Long> ids = barcodes
                .stream()
                .map(BarcodeCommand::getId)
                .collect(Collectors.toList());
        sqLiteManager.delete(ids);
    }
}
