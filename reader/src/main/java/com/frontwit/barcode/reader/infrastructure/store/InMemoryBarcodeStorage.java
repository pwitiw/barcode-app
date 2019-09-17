package com.frontwit.barcode.reader.infrastructure.store;

//public class InMemoryBarcodeStorage implements EventStore {
//
//    private Set<ProcessBarcodeCommand> commands = new HashSet<>();
//
//    @Override
//    public void save(ProcessBarcodeCommand processBarcodeCommand) {
//        commands.add(processBarcodeCommand);
//    }
//
//    @Override
//    public Collection<ProcessBarcodeCommand> findAll() {
//        return new ArrayList<>(commands);
//    }
//
//    @Override
//    public void delete(Collection<ProcessBarcodeCommand> barcodes) {
//        commands.removeIf(barcodes::contains);
//    }
//}
