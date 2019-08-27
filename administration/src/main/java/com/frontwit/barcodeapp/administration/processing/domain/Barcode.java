package com.frontwit.barcodeapp.administration.processing.domain;

import lombok.Value;

@Value
public class Barcode {

    private final static int MAX_BARCODES_PER_ORDER = 100;

    private long orderId;
    private long nr;

    public Barcode(Long barcode) {
        orderId = barcode / MAX_BARCODES_PER_ORDER;
        nr = barcode % MAX_BARCODES_PER_ORDER;
    }


}