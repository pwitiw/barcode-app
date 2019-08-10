package com.frontwit.barcode.reader.barcode.storage;

import com.frontwit.barcode.reader.barcode.BarcodeCommand;

import java.util.Collection;

// TODO komentarze
public interface BarcodeStorage {

    void save(BarcodeCommand barcodeCommand);

    Collection<BarcodeCommand> findAll();

    void delete(Collection<BarcodeCommand> barcodes);
}