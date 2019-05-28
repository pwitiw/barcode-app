package com.frontwit.barcodeapp.domain.order.dto;

import static java.lang.String.format;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(long id) {
        super(format("No order for id:%s was found", id));
    }
}
