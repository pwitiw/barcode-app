//package com.frontwit.barcode.reader.infrastructure.store;
//
//import com.frontwit.barcode.reader.application.ProcessBarcodeCommand;
//import com.frontwit.barcode.reader.application.EventStore;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//public class SQLiteBarcodeStorage implements EventStore {
//
//    private SQLiteRepository sqLiteRepository;
//
//    @Override
//    public void save(ProcessBarcodeCommand processBarcodeCommand) {
//        var readerId = processBarcodeCommand.getStage();
//        var barcode = processBarcodeCommand.getBarcode();
//        var dateTime = processBarcodeCommand.getDateTime();
//        sqLiteRepository.persist(readerId, barcode, dateTime);
//    }
//
//    @Override
//    public Map<UUID, ProcessBarcodeCommand> findAll() {
//        return sqLiteRepository.findAll();
//    }
//
//    @Override
//    public void delete(Collection<UUID> ids) {
//        sqLiteRepository.delete(ids);
//    }
//}
