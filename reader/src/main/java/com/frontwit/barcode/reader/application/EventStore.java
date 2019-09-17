package com.frontwit.barcode.reader.application;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface EventStore {

    void save(ProcessBarcodeCommand processBarcodeCommand);

    Map<UUID, ProcessBarcodeCommand> findAll();

    void delete(Collection<UUID> ids);
}