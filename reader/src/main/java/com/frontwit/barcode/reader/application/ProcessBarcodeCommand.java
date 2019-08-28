package com.frontwit.barcode.reader.application;

import com.frontwit.barcode.reader.barcode.Event;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ProcessBarcodeCommand implements Event {

    private  Integer readerId;
    private  Long barcode;
    private  LocalDateTime dateTime;
}
