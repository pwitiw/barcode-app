package com.frontwit.barcodeapp.administration.order.processing.shared;

import lombok.Value;

@Value
public class Barcode {
    private static final long LIMIT = 100;

    private Long barcode;

    public OrderId getOrderId() {
        return new OrderId(Math.floorDiv(barcode, LIMIT));
    }

    public static Barcode valueOf(OrderId orderId, long frontId) {
        return new Barcode(orderId.getOrderId() * LIMIT + frontId);
    }
}