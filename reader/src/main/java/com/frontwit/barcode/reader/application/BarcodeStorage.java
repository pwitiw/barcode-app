package com.frontwit.barcode.reader.application;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface BarcodeStorage {

    void store(ProcessFrontCommand command);

    Map<UUID, ProcessFrontCommand> findAll();

    void delete(Collection<UUID> ids);

}
