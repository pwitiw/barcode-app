package com.frontwit.barcode.reader.application;

public interface PublishBarcode {

    void publish(ProcessBarcodeCommand command) throws PublishingException;
}
