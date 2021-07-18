package com.frontwit.barcodeapp.administration.catalogue.orders.barcodes;

import com.frontwit.barcodeapp.api.shared.Barcode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayOutputStream;

@RequiredArgsConstructor
public class BarcodePdf {
    @Getter
    private final Barcode barcode;
    private final ByteArrayOutputStream output;

    public ByteArrayOutputStream asStream() {
        return output;
    }
}
