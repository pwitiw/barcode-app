package com.frontwit.barcodeapp.administration.processing.shared;

import lombok.Value;

import static java.lang.String.format;

@Value
public class Barcode {
    private static final long LIMIT = 100;

    private Long value;

    public Barcode(Long value) {
        if (value < LIMIT) {
            throw new IllegalStateException(format("Illegal barcode: %s, max value: %s", value, LIMIT));
        }
        this.value = value;
    }

    public OrderId getOrderId() {
        return new OrderId(Math.floorDiv(value, LIMIT));
    }

    public static Barcode valueOf(OrderId orderId, long frontId) {
        return new Barcode(orderId.getId() * LIMIT + frontId);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
