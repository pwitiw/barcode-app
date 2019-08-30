package com.frontwit.barcodeapp.administration.processing.model;

import lombok.Value;

@Value
public class Barcode {
    private Long value;

    public Barcode(Long barcode) {
        value = barcode;
    }
}