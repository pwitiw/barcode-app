package com.frontwit.barcodeapp.administration.catalogue.orders.barcodes;

import lombok.AllArgsConstructor;

import java.io.ByteArrayOutputStream;

@AllArgsConstructor
public class BarcodePdf {
    private final ByteArrayOutputStream output;

    public ByteArrayOutputStream asStream() {
        return output;
    }
}
