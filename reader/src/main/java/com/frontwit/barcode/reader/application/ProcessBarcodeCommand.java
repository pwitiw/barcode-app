package com.frontwit.barcode.reader.application;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ProcessBarcodeCommand {

    private  Integer stage;
    private  Long barcode;
    private  LocalDateTime dateTime;
}
