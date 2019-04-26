package com.frontwit.barcodeapp.domain.order.processing.dto;

import static java.lang.String.format;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(long barcode) {
        super(format("No order for barcode:%s found", barcode));
    }
}
