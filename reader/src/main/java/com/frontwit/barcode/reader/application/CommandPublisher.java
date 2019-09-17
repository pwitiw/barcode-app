package com.frontwit.barcode.reader.application;

public interface CommandPublisher {

    void publish(ProcessBarcodeCommand processBarcodeCommand);
}
