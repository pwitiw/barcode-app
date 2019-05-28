package com.frontwit.barcodeapp.domain.order;

public class BarcodeConverter {

    static final long MAX_COMPONENTS = 100L;

    public static long toBarcode(long id) {
        return id * MAX_COMPONENTS;
    }

    static long toId(long barcode) {
        return barcode / MAX_COMPONENTS;
    }
}
