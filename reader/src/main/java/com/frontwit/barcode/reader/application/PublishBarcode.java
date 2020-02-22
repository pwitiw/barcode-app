package com.frontwit.barcode.reader.application;

public interface PublishBarcode {

    void publish(ProcessFrontCommand command) throws PublishingException;
}
