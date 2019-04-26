package com.frontwit.barcodeapp.domain.order.processing;

public class BarcodeGenerator {

    public static final long MAX_COMPONENTS = 100L;

    public static long create(long extId) {
        return extId * MAX_COMPONENTS;
    }
}
