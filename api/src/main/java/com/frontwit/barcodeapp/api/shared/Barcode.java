package com.frontwit.barcodeapp.api.shared;

import lombok.Value;

import static java.lang.String.format;

@Value
@SuppressWarnings("PMD.AvoidFieldNameMatchingTypeName")
public class Barcode {
    private static final long LIMIT = 100;

    private final Long barcode;

    public Barcode(Long barcode) {
        if (barcode < LIMIT) {
            throw new IllegalStateException(format("Illegal barcode: %s, max value: %s", barcode, LIMIT));
        }
        this.barcode = barcode;
    }

    public OrderId getOrderId() {
        return new OrderId(Math.floorDiv(barcode, LIMIT));
    }

    public static Barcode valueOf(OrderId orderId, long frontId) {
        return new Barcode(orderId.getId() * LIMIT + frontId);
    }

    @Override
    public String toString() {
        return String.valueOf(barcode);
    }
}
