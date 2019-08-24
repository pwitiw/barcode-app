package com.frontwit.barcodeapp.administration.application.order;

// TODO czy potrzebne to?
public class BarcodeConverterImpl implements BarcodeConverter {

    static final long MAX_COMPONENTS = 100L;

    public long toBarcode(long id) {
        return id * MAX_COMPONENTS;
    }

    static long toId(long barcode) {
        return barcode / MAX_COMPONENTS;
    }
}
