package com.frontwit.barcodeapp.administration.processing.front.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProcessFront {
    private Barcode barcode;
    private Stage stage;
    private LocalDateTime dateTime;
}
