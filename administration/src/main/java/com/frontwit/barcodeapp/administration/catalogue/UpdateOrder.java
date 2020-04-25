package com.frontwit.barcodeapp.administration.catalogue;

import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
class UpdateOrder {
    private Long orderId;
    private Instant deadline;
        private BigDecimal price;

    static UpdateOrder of(long orderId, long deadline, BigDecimal price) {
        return new UpdateOrder(orderId, Instant.ofEpochMilli(deadline), price);
    }
}