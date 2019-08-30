package com.frontwit.barcodeapp.administration.processing.front.model;

import lombok.Value;

@Value
public class Barcode {
    private Long value;

    public Barcode(Long barcode) {
        value = barcode;
    }
}