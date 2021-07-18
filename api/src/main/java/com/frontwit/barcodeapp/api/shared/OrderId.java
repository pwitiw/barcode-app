package com.frontwit.barcodeapp.api.shared;

import lombok.Value;

@Value
public class OrderId {

    private final long id;

    public OrderId(long id) {
        if (id <= 0) {
            throw new IllegalStateException("Id must be positive value.");
        }
        this.id = id;
    }
}
