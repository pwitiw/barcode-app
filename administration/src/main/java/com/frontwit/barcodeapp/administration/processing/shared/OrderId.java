package com.frontwit.barcodeapp.administration.processing.shared;

import lombok.Value;

@Value
public class OrderId {

    private long orderId;

    public OrderId(long orderId) {
        if (orderId <= 0) {
            throw new IllegalStateException("Id must be positive value.");
        }
        this.orderId = orderId;
    }
}
