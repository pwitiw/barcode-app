package com.frontwit.barcodeapp.administration.catalogue;

import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
class UpdateOrder {
    private Long orderId;
    private Instant deadline;
        private BigDecimal valuation;

    static UpdateOrder of(long orderId, long deadline, BigDecimal valuation) {
        return new UpdateOrder(orderId, Instant.ofEpochMilli(deadline), valuation);
    }
}