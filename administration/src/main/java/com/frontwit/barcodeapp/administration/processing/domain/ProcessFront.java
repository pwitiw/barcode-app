package com.frontwit.barcodeapp.administration.processing.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProcessFront {
    private Barcode barcode;
    private Status status;
    private LocalDateTime dateTime;
}
