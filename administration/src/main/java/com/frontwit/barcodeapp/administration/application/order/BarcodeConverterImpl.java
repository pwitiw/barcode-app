package com.frontwit.barcodeapp.administration.application.order;

import org.springframework.stereotype.Component;

@Component
public class BarcodeConverterImpl implements BarcodeConverter {

    static final long MAX_COMPONENTS = 100L;

    @Override
    public long toBarcode(long id) {
        return id * MAX_COMPONENTS;
    }

    @Override
    public long toId(long barcode) {
        return barcode / MAX_COMPONENTS;
    }
}
