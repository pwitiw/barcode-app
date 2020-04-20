package com.frontwit.barcode.reader.application;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ProcessFrontCommand {

    private  Integer stage;
    private  Long barcode;
    private  LocalDateTime dateTime;
}
